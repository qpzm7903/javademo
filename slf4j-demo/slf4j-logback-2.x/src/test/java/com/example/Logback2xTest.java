package com.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback2xTest {
    private static final Logger logger = LoggerFactory.getLogger(Logback2xTest.class);

    @Test
    public void testLogging() {
        // 1. 基础日志测试
        logger.info("=== 测试 SLF4J 2.x + Logback ===");
        
        // 2. Fluent Logging API
        logger.atInfo()
              .addKeyValue("user", "张三")
              .addKeyValue("age", 25)
              .log("用户信息");

        // 3. Lambda 表达式延迟日志
        // 只有当日志级别启用时才会执行昂贵的操作
        logger.atDebug()
              .setMessage("昂贵的操作结果: {}")
              .addArgument(() -> performExpensiveOperation())
              .log();

        // 4. String.format 风格的格式化
        logger.info("用户 %s 的年龄是 %d", "张三", 25);

        // 5. 使用 Supplier 进行延迟计算
        logger.atInfo()
              .setMessage(() -> "这是一个延迟计算的消息 - " + System.currentTimeMillis())
              .log();

        // 6. 组合使用新特性
        logger.atWarn()
              .setCause(new RuntimeException("测试异常"))
              .addKeyValue("timestamp", System.currentTimeMillis())
              .log("发生异常");

        // 7. 条件日志记录
        logger.atInfo()
              .setMessage("条件日志消息")
              .addArgument(() -> getArgument())
              .log();
    }

    private String performExpensiveOperation() {
        // 模拟耗时操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "耗时操作的结果";
    }

    private String getArgument() {
        return "动态参数 - " + System.currentTimeMillis();
    }
} 