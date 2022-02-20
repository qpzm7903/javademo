package com.qpzm7903.validation.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-12-17:11
 */
@RestController
@Slf4j
public class TestController {

    @PostMapping("/form")
    public String form(@Validated @RequestBody Person person) {
        log.info("person is " + person);

        return "注册成功!";
    }
}
