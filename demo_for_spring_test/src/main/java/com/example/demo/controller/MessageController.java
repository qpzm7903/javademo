package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Objects;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-28-21:17
 */

@RestController
public class MessageController {
    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String getMessage(@RequestParam("key") String key) {
        return messageSource.getMessage(key, null, Locale.US);
    }
}

