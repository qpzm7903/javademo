package com.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback17Test {
    private static final Logger logger = LoggerFactory.getLogger(Logback17Test.class);

    @Test
    public void testLogging() {
        logger.info("=== 测试 SLF4J 1.7.x + Logback ===");
        logger.trace("这是一条 TRACE 日志");
        logger.debug("这是一条 DEBUG 日志");
        logger.info("这是一条 INFO 日志");
        logger.warn("这是一条 WARN 日志");
        logger.error("这是一条 ERROR 日志");

        // 测试参数化日志
        String name = "张三";
        int age = 25;
        logger.info("用户 {} 的年龄是 {}", name, age);

        // 测试异常日志
        try {
            throw new RuntimeException("测试异常");
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
    }
} 