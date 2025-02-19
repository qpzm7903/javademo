package com.qpzm7903.base20.concept01;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * Reactive Streams规范测试
 */
public class ReactiveStreamsDemoTest {
    
    private ReactiveStreamsDemo demo = new ReactiveStreamsDemo();
    
    // @Test
    void testFileReading() {
        // 创建测试文件
        String testFilePath = createTestFile();
        
        // 测试响应式读取
        StepVerifier.create(demo.readFileReactive(testFilePath))
                .expectNext("Line 1")
                .expectNext("Line 2")
                .expectNext("Line 3")
                .verifyComplete();
    }
    
    private String createTestFile() {
        // 创建测试文件的逻辑
        return "test.txt";
    }
} 