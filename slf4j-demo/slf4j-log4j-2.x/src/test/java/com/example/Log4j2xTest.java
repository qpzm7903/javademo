package com.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2xTest {
    private static final Logger logger = LoggerFactory.getLogger(Log4j2xTest.class);
    
    @Test
    public void testLogging() {
        // 1. 基础日志测试
        logger.info("=== 测试 SLF4J 2.x + Log4j ===");
        
        // 2. Fluent Logging API
        logger.atInfo()
                .addKeyValue("user", "张三")
                .addKeyValue("age", 25)
                .log("用户信息");
        
        // 3. 延迟日志计算
        logger.atDebug()
                .setMessage("昂贵的操作结果: {}")
                .addArgument(this::performExpensiveOperation)
                .log();
        
        // 4. 带异常的 Fluent API
        try {
            throw new RuntimeException("测试异常");
        } catch (Exception e) {
            logger.atError()
                    .setCause(e)
                    .addKeyValue("errorCode", "E001")
                    .addKeyValue("timestamp", System.currentTimeMillis())
                    .log("发生异常");
        }
        
        // 5. 条件日志记录
        logger.atInfo()
                .setMessage("条件日志消息 - {}")
                .addArgument(this::getArgument)
                .log();
    }
    
    private String performExpensiveOperation() {
        // 模拟耗时操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
        }
        return "耗时操作的结果";
    }
    
    private String getArgument() {
        return "动态参数 - " + System.currentTimeMillis();
    }
} 