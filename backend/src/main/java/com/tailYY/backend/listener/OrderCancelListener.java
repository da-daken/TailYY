package com.tailYY.backend.listener;

import com.tailYY.backend.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author daken 2025/1/25
 **/
@Component
@Slf4j
public class OrderCancelListener {

    @Resource
    private OrderInfoService orderService;

    private static final String ORDER_CANCEL_KEY_PREFIX = "order:cancel:";

    public void handleMessage(String message) {
        // message 是过期的键名
        if (message.startsWith(ORDER_CANCEL_KEY_PREFIX)) {
            String orderId = message.substring(ORDER_CANCEL_KEY_PREFIX.length());
            log.info("收到订单信息 {} 执行取消操作", orderId);
            // 在这里执行取消订单的业务逻辑
            cancelOrder(Long.valueOf(orderId));
        }
    }

    private void cancelOrder(Long orderId) {
        // 实现具体的取消订单逻辑
        orderService.cancelOrder(orderId);
    }
}
