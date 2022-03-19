package com.ygq.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * <p>
 * 测试用
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 18:53
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/employee/basic/hello")
    public String hello2() {
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello3() {
        return "/employee/advanced/hello";
    }
}
