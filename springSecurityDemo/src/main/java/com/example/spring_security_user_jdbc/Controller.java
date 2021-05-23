package com.example.spring_security_user_jdbc;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-22 21:52
 */

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/boss/")
    String boss() {
        return "boss";
    }

    @GetMapping("/employee/")
    String employee() {
        return "employee";
    }
}
