package com.example.transaction_demo.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/posts")
    String getPosts() {
        return "post";
    }
    
    @GetMapping(value = "/posts:getPostName")
    String getPostName() {
        return "getPostName";
    }
    
}
