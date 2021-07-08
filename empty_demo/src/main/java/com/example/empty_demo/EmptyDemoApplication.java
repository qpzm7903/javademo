package com.example.empty_demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmptyDemoApplication implements CommandLineRunner {
    private static final Log log = LogFactory.getLog(EmptyDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EmptyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("test");

    }
}
