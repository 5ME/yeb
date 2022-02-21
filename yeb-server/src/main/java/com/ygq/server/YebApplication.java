package com.ygq.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * YebApplication
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-16 21:19
 */
@MapperScan("com.ygq.server.mapper")
@SpringBootApplication
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);
    }
}
