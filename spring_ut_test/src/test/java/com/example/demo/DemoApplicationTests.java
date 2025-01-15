package com.example.demo;

import com.example.demo.util.SpringContextUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    SpringContextUtil springContextUtil;

    @Test
    void contextLoads() {
        assert  springContextUtil != null;
    }

    @Test
    void test_get_factory_bean() {
        ApplicationContext context = springContextUtil.getContext();
        Object bean = context.getBean("&testService");
        System.out.println(bean);
    }

}
