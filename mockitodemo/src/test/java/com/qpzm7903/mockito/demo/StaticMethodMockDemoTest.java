package com.qpzm7903.mockito.demo;

import com.qpzm7903.mockito.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

public class StaticMethodMockDemoTest {
    @Test
    void test_mock_a_static_method() {
        try (MockedStatic<StringUtils> mockedStatic = mockStatic(StringUtils.class)) {
            mockedStatic.when(() -> StringUtils.combineString("a", "b"))
                    .thenReturn("1+2");
            Assertions.assertEquals("1+2", StringUtils.combineString("a", "b"));
            // mock后，其他没有mock的就返回null
            Assertions.assertNull(StringUtils.combineString("b", "c"));
        }
    }
    
    @Test
    void test_mock_no_return_value_static_method() {
        // 使用 mockStatic 来 mock 静态方法
        try (var mockedStatic = mockStatic(StringUtils.class)) {
            // 设置静态方法的行为
            mockedStatic.when(() -> StringUtils.logInfo(anyString()))
                    .thenAnswer(invocation -> {
                        // 自定义静态方法行为，可以选择打印或者返回特定值
                        System.out.println("Mocked log: " + invocation.getArgument(0));
                        return null; // 静态方法没有返回值
                    });
            // 调用静态方法
            StringUtils.logInfo("Test message");
            // 验证静态方法是否被调用
            mockedStatic.verify(() -> StringUtils.logInfo("Test message"));
        }
    }
    
    @Test
    public void testLogInfoThrowsRuntimeException() {
        // Mock静态方法
        try (var mockedStatic = mockStatic(StringUtils.class)) {
            // 配置logInfo方法抛出RuntimeException
            mockedStatic.when(() -> StringUtils.logInfo(anyString()))
                    .thenThrow(new RuntimeException("Mocked exception"));
            
            // 调用静态方法并捕获异常
            try {
                StringUtils.logInfo("Test message");
            } catch (RuntimeException e) {
                // 验证异常是否符合预期
                System.out.println("Caught exception: " + e.getMessage());
                assert e.getMessage()
                        .equals("Mocked exception");
            }
            
            // 验证静态方法是否被调用
            mockedStatic.verify(() -> StringUtils.logInfo("Test message"));
        }
    }
    
    @Test
    public void testThenCallRealMethod() {
        // Mock 静态方法
        try (var mockedStatic = mockStatic(StringUtils.class)) {
            // 配置 combineString 调用真实方法
            mockedStatic.when(() -> StringUtils.combineString(anyString(), anyString()))
                    .thenCallRealMethod();
            
            // 验证结果
            Assertions.assertEquals(StringUtils.combineString("Hello, ", "World!"), "Hello, World!");
            
            // 验证方法是否被调用
            mockedStatic.verify(() -> StringUtils.combineString("Hello, ", "World!"));
            
            // 配置 combineString 调用真实方法
            mockedStatic.when(() -> StringUtils.combineString(anyString(), anyString()))
                    .thenReturn("MockValue");
            // 恢复mock
            Assertions.assertEquals(StringUtils.combineString("Hello, ", "World!"), "MockValue");
            
        }
    }
    
}
