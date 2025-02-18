package com.tailYY.backend.controller;

import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.exception.BusinessException;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author daken 2025/2/9
 **/
@RestController
public class FileUploadController {
    private static final String UPLOAD_DIR = "backend/src/main/resources/static/";

    @PostMapping("/uploadPhoto")
    public BaseResponse<String> uploadPhoto(@RequestParam("photo") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        try {
            // 确保保存目录存在
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            // 获取文件名并构建目标路径
            String fileName = file.getOriginalFilename();
            // 获取文件名的前缀和后缀
            int dotIndex = fileName.lastIndexOf('.');
            String prefix = fileName.substring(0, dotIndex);
            String suffix = fileName.substring(dotIndex);

            // 生成时间戳作为后缀
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String newFileName = prefix + "_" + timeStamp + suffix;
            Path path = Paths.get(UPLOAD_DIR + newFileName);

            // 将文件保存到指定位置
            Files.copy(file.getInputStream(), path);

            return ResultUtils.success(UPLOAD_DIR + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultUtils.success("上传失败");
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
    }
}
