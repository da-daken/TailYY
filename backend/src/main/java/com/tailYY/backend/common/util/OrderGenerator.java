package com.tailYY.backend.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daken 2025/1/16
 **/
public class OrderGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private static final AtomicInteger sequence = new AtomicInteger(0);
    private static final int SEQUENCE_MAX = 999; // 序列号最大值，可根据需要调整
    private static final Object lock = new Object();

    /**
     * 生成带有时间戳和序列号的订单号。
     *
     * @return 格式化后的订单号字符串
     */
    public static synchronized Long generateOrderId(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DATE_FORMATTER);

        // 使用AtomicInteger保证线程安全，并且当达到最大序列号时重置
        int seq = sequence.incrementAndGet();
        if (seq > SEQUENCE_MAX) {
            sequence.set(0); // 重置序列号为0
            return generateOrderId(userId); // 重新尝试生成以避免重复
        }

        // 返回组合后的订单号
        return Long.valueOf(timestamp + userId + String.format("%03d", seq)); // 序列号占3位，不足补零
    }

    /**
     * 计算当前时间加上过期时间后的日期。
     *
     * @param expireTime 过期时间，单位是小时
     * @return 过期后的日期时间
     */
    public static Date calculateExpirationTime(Integer expireTime) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 创建一个表示过期时间的 Duration 对象
        Duration duration = Duration.ofHours(expireTime);

        // 将 Duration 添加到当前时间
        LocalDateTime expirationLocalDateTime = now.plus(duration);

        // 转换为 Instant 并指定时区（这里假设使用系统默认时区）
        return Date.from(expirationLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
