package com.example.demo.init;

import com.example.demo.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-05-12-6:08
 */
@Component
public class TestCommandLineRunner implements ApplicationRunner {

    @Autowired
    private MyProperties myProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(myProperties);
    }
}
