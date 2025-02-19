package com.qpzm7903.base20.concept17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 组合与分解操作测试
 */
public class CombinationDecompositionDemoTest {
    
    private CombinationDecompositionDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new CombinationDecompositionDemo();
        demo.resetCounter();
    }
    
    /**
     * 测试基础组合<br>
     * 验证zip操作
     */
    @Test
    void testBasicCombination() {
        StepVerifier.create(demo.basicCombination())
                .expectNext("A1", "B2", "C3")
                .verifyComplete();
        
        assertEquals(3, demo.getOperationCount());
    }
    
    /**
     * 测试合并流<br>
     * 验证merge操作
     */
    @Test
    void testMergeStreams() {
        List<String> expected = List.of("X", "Y", "Z", "1", "2", "3");
        
        StepVerifier.create(demo.mergeStreams())
                .expectNextCount(6)
                .verifyComplete();
        
        assertEquals(6, demo.getOperationCount());
    }
    
    /**
     * 测试串联流<br>
     * 验证concat操作
     */
    @Test
    void testConcatenateStreams() {
        StepVerifier.create(demo.concatenateStreams())
                .expectNext("First", "Second", "Third", "Fourth")
                .verifyComplete();
        
        assertEquals(4, demo.getOperationCount());
    }
    
    /**
     * 测试分组<br>
     * 验证groupBy操作
     */
    @Test
    void testGroupElements() {
        AtomicInteger groupCount = new AtomicInteger(0);
        
        demo.groupElements()
                .flatMap(groupedData -> {
                    groupCount.incrementAndGet();
                    return groupedData.getItems().count();
                })
                .as(StepVerifier::create)
                .expectNext(2L, 2L)  // 每个组有2个元素
                .verifyComplete();
        
        assertEquals(2, groupCount.get());  // 应该有2个组
        assertEquals(2, demo.getOperationCount());
    }
    
    /**
     * 测试窗口<br>
     * 验证window操作
     */
    @Test
    void testWindowElements() {
        StepVerifier.create(demo.windowElements())
                .expectNext(List.of(1, 2, 3))
                .expectNext(List.of(4, 5, 6))
                .expectNext(List.of(7, 8, 9))
                .expectNext(List.of(10))
                .verifyComplete();
        
        assertEquals(4, demo.getOperationCount());
    }
    
    /**
     * 测试条件组合<br>
     * 验证条件流选择
     */
    @Test
    void testConditionalCombination() {
        StepVerifier.create(demo.conditionalCombination(true))
                .expectNext("First1", "First2")
                .verifyComplete();
        
        demo.resetCounter();
        
        StepVerifier.create(demo.conditionalCombination(false))
                .expectNext("Second1", "Second2")
                .verifyComplete();
        
        assertEquals(2, demo.getOperationCount());
    }
    
    /**
     * 测试扁平化<br>
     * 验证flatMap操作
     */
    @Test
    void testFlattenStreams() {
        StepVerifier.create(demo.flattenStreams())
                .expectNext("A1", "A2", "B1", "B2")
                .verifyComplete();
        
        assertEquals(4, demo.getOperationCount());
    }
    
    /**
     * 测试批处理<br>
     * 验证buffer操作
     */
    @Test
    void testBatchProcessing() {
        StepVerifier.create(demo.batchProcessing())
                .expectNext(List.of(1, 2, 3))
                .expectNext(List.of(4, 5, 6))
                .expectNext(List.of(7, 8, 9))
                .expectNext(List.of(10))
                .verifyComplete();
        
        assertEquals(4, demo.getOperationCount());
    }
} 