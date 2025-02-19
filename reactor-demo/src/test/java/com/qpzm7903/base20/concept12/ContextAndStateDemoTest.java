package com.qpzm7903.base20.concept12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Context与状态传递测试
 */
public class ContextAndStateDemoTest {
    
    private ContextAndStateDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new ContextAndStateDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试基础Context使用<br>
     * 验证Context的读写操作
     */
    @Test
    void testBasicContext() {
        StepVerifier.create(demo.basicContextExample())
                .expectNext("John processed data")
                .verifyComplete();
        
        assertEquals(1, demo.getContextReadCount());
        assertEquals(1, demo.getContextWriteCount());
    }
    
    /**
     * 测试多层Context<br>
     * 验证Context的层级访问
     */
    @Test
    void testMultiLayerContext() {
        StepVerifier.create(demo.multiLayerContext())
                .expectNextMatches(map -> {
                    return "John".equals(map.get("user")) &&
                            "admin".equals(map.get("role"));
                })
                .verifyComplete();
        
        assertEquals(1, demo.getContextReadCount());
    }
    
    /**
     * 测试Context状态传递<br>
     * 验证状态在流中的传递
     */
    @Test
    void testContextWithState() {
        List<String> results = new ArrayList<>();
        
        demo.contextWithState()
                .subscribe(results::add);
        
        assertEquals(3, results.size());
        assertTrue(results.stream()
                .allMatch(s -> s.contains("transaction TX-")));
        assertEquals(3, demo.getContextReadCount());
        assertEquals(1, demo.getContextWriteCount());
    }
    
    /**
     * 测试动态Context更新<br>
     * 验证Context的动态变化
     */
    @Test
    void testDynamicContextUpdate() {
        List<String> results = new ArrayList<>();
        
        demo.dynamicContextUpdate()
                .subscribe(results::add);
        
        assertEquals(3, results.size());
        assertEquals("Step 1 (Count: 0)", results.get(0));
        assertEquals("Step 2 (Count: 1)", results.get(1));
        assertEquals("Step 3 (Count: 2)", results.get(2));
    }
    
    /**
     * 测试Context合并<br>
     * 验证多个Context值的合并
     */
    @Test
    void testMergedContext() {
        StepVerifier.create(demo.mergedContext())
                .expectNext("User: John, Role: admin, Region: EU")
                .verifyComplete();
        
        assertEquals(1, demo.getContextReadCount());
        assertEquals(1, demo.getContextWriteCount());
    }
    
    /**
     * 测试条件Context<br>
     * 验证条件性Context添加
     */
    @Test
    void testConditionalContext() {
        // 测试添加额外Context
        StepVerifier.create(demo.conditionalContext(true))
                .expectNext("Base: basic, Extra: additional")
                .verifyComplete();
        
        demo.resetCounters();
        
        // 测试不添加额外Context
        StepVerifier.create(demo.conditionalContext(false))
                .expectNext("Base: basic")
                .verifyComplete();
        
        assertEquals(1, demo.getContextReadCount());
        assertEquals(1, demo.getContextWriteCount());
    }
} 