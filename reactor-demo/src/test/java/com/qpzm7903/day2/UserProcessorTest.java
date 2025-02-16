package com.qpzm7903.day2;

import com.qpzm7903.day2.model.User;
import com.qpzm7903.day2.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserProcessorTest {
    
    private UserProcessor processor;
    private List<User> testUsers;
    private static final Logger logger = LoggerFactory.getLogger(UserProcessorTest.class);
    
    @BeforeEach
    void setUp() {
        processor = new UserProcessor();
        testUsers = Arrays.asList(new User(1L, "John", 25, "john@example.com"),
                new User(2L, "Alice", 17, "alice@example.com"), new User(3L, "Bob", 30, "bob@example.com"),
                new User(4L, "Eve", 22, "eve@example.com"));
    }
    
    @Test
    void testReactiveProcessing() {
        Flux<User> userFlux = Flux.fromIterable(testUsers);
        
        StepVerifier.create(processor.processUsersReactive(userFlux))
                .expectNextCount(3)  // 期望3个成年用户
                .verifyComplete();
    }
    
    @Test
    void testImperativeProcessing() {
        List<UserDTO> result = processor.processUsersImperative(testUsers);
        assertEquals(3, result.size());  // 验证成年用户数量
        
        // 验证邮箱脱敏
        result.forEach(dto -> assertTrue(dto.getEmailMasked()
                .contains("***")));
    }
    
    @Test
    void comparePerformance() {
        // 创建大量测试数据
        List<User> largeUserList = generateLargeUserList(10000);
        
        // 测试响应式处理性能
        long reactiveStart = System.currentTimeMillis();
        Flux<User> userFlux = Flux.fromIterable(largeUserList);
        processor.processUsersReactive(userFlux)
                .blockLast(Duration.ofSeconds(10));
        long reactiveDuration = System.currentTimeMillis() - reactiveStart;
        
        // 测试命令式处理性能
        long imperativeStart = System.currentTimeMillis();
        processor.processUsersImperative(largeUserList);
        long imperativeDuration = System.currentTimeMillis() - imperativeStart;
        
        System.out.println("Reactive processing time: " + reactiveDuration + "ms");
        System.out.println("Imperative processing time: " + imperativeDuration + "ms");
    }
    
    @Test
    void comparePerformanceWithAsyncOperations() {
        List<User> largeUserList = generateLargeUserList(100);
        
        // 测试响应式处理性能
        long reactiveStart = System.currentTimeMillis();
        Flux<User> userFlux = Flux.fromIterable(largeUserList)
                .flatMap(this::simulateAsyncOperation)  // 模拟异步操作
                .parallel(4)  // 使用4个并行线程
                .runOn(Schedulers.parallel())
                .sequential();  // Convert ParallelFlux back to Flux
        
        processor.processUsersReactive(userFlux)
                .blockLast(Duration.ofSeconds(10));
        long reactiveDuration = System.currentTimeMillis() - reactiveStart;
        
        // 测试命令式处理性能
        long imperativeStart = System.currentTimeMillis();
        List<User> processedUsers = largeUserList.stream()
                .parallel()  // 添加并行处理
                .map(user -> {
                    try {
                        return simulateBlockingOperation(user);  // 模拟阻塞操作
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        processor.processUsersImperative(processedUsers);
        long imperativeDuration = System.currentTimeMillis() - imperativeStart;
        
        System.out.println("Reactive processing time: " + reactiveDuration + "ms");
        System.out.println("Imperative processing time: " + imperativeDuration + "ms");
    }
    
    @Test
    void testErrorHandlingInFilter() {
        List<User> usersWithNull = Arrays.asList(null,  // 将触发 NullPointerException
                new User(1L, "John", 25, "john@example.com"), new User(3L, "Bob", 30, "bob@example.com"));
        
        Flux<User> userFlux = Flux.fromIterable(usersWithNull);
        
        StepVerifier.create(processor.processUsersReactive(userFlux))
                .expectErrorMessage("The iterator returned a null value")
                .verify();
    }
    
    @Test
    void testErrorHandlingWithMultipleErrors() {
        List<User> problematicUsers = Arrays.asList(new User(1L, "John", 25, "john@example.com"),
                new User(3L, "Bob", -1, "bob@example.com"), new User(5L, "Alice", 28, "alice@example.com"));
        
        Flux<UserDTO> publisher = processor.processUsersReactive(Flux.fromIterable(problematicUsers));
        StepVerifier.create(publisher)
                .expectNextCount(2)  // 期望处理3个正常的用户数据
                .verifyComplete();
    }
    
    // 模拟异步操作
    private Mono<User> simulateAsyncOperation(User user) {
        return Mono.just(user)
                .delayElement(Duration.ofMillis(100))  // 模拟网络延迟
                .map(u -> {
                    // 模拟一些计算密集型操作
                    u.setEmail(enrichUserEmail(u.getEmail()));
                    return u;
                });
    }
    
    // 模拟阻塞操作
    private User simulateBlockingOperation(User user) throws InterruptedException {
        Thread.sleep(100);  // 模拟网络延迟
        user.setEmail(enrichUserEmail(user.getEmail()));
        return user;
    }
    
    private String enrichUserEmail(String email) {
        // 模拟一些CPU密集型操作
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result.append(email.charAt(i % email.length()));
        }
        return result.toString();
    }
    
    private List<User> generateLargeUserList(int count) {
        return Flux.range(1, count)
                .map(i -> new User((long) i, "User" + i, 18 + (i % 50), "user" + i + "@example.com"))
                .collectList()
                .block();
    }
} 