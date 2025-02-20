package com.tailYY.backend.common.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author daken 2025/2/20
 **/
@Slf4j
public class FileUtils {

    /**
     * 读取本地文件转换成base64流
     */
    public static String convertFileToBase64(String filePath) {
        try {
            // 读取文件内容为字节数组
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

            // 将字节数组转换为 Base64 编码的字符串
            String base64String = Base64.getEncoder().encodeToString(fileContent);

            log.info("Base64 encoded string: {}", base64String);
            // 输出 Base64 编码的字符串
            System.out.println("Base64 encoded string: " + base64String);
            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        convertFileToBase64("backend/src/main/resources/static/haha.jpg");
    }
}
