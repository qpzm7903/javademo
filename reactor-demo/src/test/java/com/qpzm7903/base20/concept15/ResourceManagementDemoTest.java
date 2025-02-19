package com.qpzm7903.base20.concept15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 资源管理与清理测试
 */
public class ResourceManagementDemoTest {
    
    private ResourceManagementDemo demo;
    
    @BeforeEach
    void setUp() {
        demo = new ResourceManagementDemo();
        demo.resetCounters();
    }
    
    /**
     * 测试基础资源管理<br>
     * 验证资源的创建和清理
     */
    @Test
    void testBasicResourceManagement() {
        StepVerifier.create(demo.basicResourceManagement())
                .expectNextMatches(data -> data.startsWith("Data from Basic"))
                .verifyComplete();
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
    
    /**
     * 测试延迟资源清理<br>
     * 验证延迟清理行为
     */
    @Test
    void testDeferredCleanup() {
        AtomicReference<ResourceManagementDemo.Resource> resourceRef = new AtomicReference<>();
        
        demo.deferredCleanup()
                .doOnNext(data -> {
                    // 资源应该在数据发出时仍然打开
                    assertFalse(resourceRef.get().isClosed());
                })
                .block(Duration.ofSeconds(1));
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
    
    /**
     * 测试多资源管理<br>
     * 验证多个资源的处理
     */
    @Test
    void testMultipleResources() {
        StepVerifier.create(demo.multipleResources())
                .expectNextCount(3)
                .verifyComplete();
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
    
    /**
     * 测试条件资源清理<br>
     * 验证条件清理行为
     */
    @Test
    void testConditionalCleanup() {
        // 测试清理场景
        demo.conditionalCleanup(true)
                .block(Duration.ofSeconds(1));
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
        
        demo.resetCounters();
        
        // 测试不清理场景
        demo.conditionalCleanup(false)
                .block(Duration.ofSeconds(1));
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(0, demo.getResourcesClosed());
    }
    
    /**
     * 测试异步资源管理<br>
     * 验证异步环境中的资源管理
     */
    @Test
    void testAsyncResourceManagement() {
        StepVerifier.create(demo.asyncResourceManagement())
                .expectNextMatches(data -> data.startsWith("Data from Async"))
                .verifyComplete();
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
    
    /**
     * 测试资源池管理<br>
     * 验证资源池的行为
     */
    @Test
    void testResourcePool() {
        StepVerifier.create(demo.resourcePool(3))
                .expectNextCount(3)
                .verifyComplete();
        
        assertEquals(3, demo.getResourcesCreated());
        assertEquals(3, demo.getResourcesClosed());
    }
    
    /**
     * 测试错误处理资源管理<br>
     * 验证错误场景下的资源清理
     */
    @Test
    void testErrorHandlingResource() {
        StepVerifier.create(demo.errorHandlingResource())
                .expectNextMatches(data -> 
                    data.startsWith("Data from Error") || data.equals("Error handled"))
                .verifyComplete();
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
    
    /**
     * 测试资源重用<br>
     * 验证资源重用行为
     */
    @Test
    void testResourceReuse() {
        StepVerifier.create(demo.resourceReuse())
                .expectNextCount(3)
                .verifyComplete();
        
        assertEquals(1, demo.getResourcesCreated());
        assertEquals(1, demo.getResourcesClosed());
    }
} 