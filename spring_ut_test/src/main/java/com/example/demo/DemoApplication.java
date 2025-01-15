package com.example.demo;

import com.example.demo.config.MyProperties;
import com.example.test.demo.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.test"})
@RestController
@ConfigurationPropertiesScan
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);

    }



    @Configuration
    static public class Config{

    }
    @Autowired
    TestService testService;


    @Value("${application}")
    String application;


    @Value("${name}")
    String name;

    @Value("${value}")
    String value;


    @Value("${default}")
    String default_value;

    @RequestMapping("/test")
    String test() {
        return default_value + " \n"  + testService.test() + application + name + value;
    }


}
