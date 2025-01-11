package com.tailYY.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author daken 2025/1/11
 **/

@SpringBootApplication
@EnableRedisHttpSession
public class TailYYApplication {

    public static void main(String[] args) {
        SpringApplication.run(TailYYApplication.class, args);
        System.out.println("尾巴摇摇宠物店启动！！！！" +
                "\n 谁与争锋" +
                "\n 唯快不破");
    }

}
