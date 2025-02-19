package com.tailYY.backend.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/**
 * @author daken 2025/2/19
 **/
@Slf4j
public class GitHubFileUtil {
    /**
     * 上传文件
     *
     * @param filepath    文件地址
     * @param filePostfix 文件后缀
     * @param message     提交描述
     * @return 文件访问地址
     */
    public static String uploading(String filepath, String filePostfix, String message) {
        // 把上传文件内容Base64
        String fileBase64 = encryptToBase64(filepath);
        // 重置文件名防止重复
        String fileName = UUID.randomUUID().toString().replace("-", "") + filePostfix;
        JSONObject param = new JSONObject();
        param.put("message", message);// 提交描述
        param.put("content", fileBase64);// Base64过的文件
        param.put("branch", Param.branch);// 上传到仓库的分支，以前是master;2020.10.01开始就变成了main了
        JSONObject committer = new JSONObject();
        committer.put("name", Param.name); // 提交的作者或提交者的姓名
        committer.put("email", Param.email);// 提交的作者或提交者的电子邮件
        param.put("committer", committer);
        param.put("sha", "");// 添加的时候没有参数也要写""
        String url = "https://api.github.com/repos/OWNER/REPO/contents/PATH";
        url = url.replace("OWNER", Param.OWNER)// 存储库的帐户所有者。名称不区分大小写
                .replace("REPO", Param.REPO)// 仓库名称
                .replace("PATH", Param.PATH);// 存储仓库里面的路径
        url = url + fileName;

        HttpResponse response = HttpRequest.put(url)
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", Param.Authorization).body(param.toString()).execute();
        log.info("响应结果：{}", response.body());
        JSONObject jsonObject = JSONObject.parseObject(response.body());
        // 取出访问地址
        JSONObject content = jsonObject.getJSONObject("content");
        String download_url = content.getString("download_url");
        return download_url;
    }


    /**
     * 文件转base64
     *
     * @param filePath 文件地址
     * @return
     */
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
