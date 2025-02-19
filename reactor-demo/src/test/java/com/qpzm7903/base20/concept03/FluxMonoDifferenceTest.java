package com.qpzm7903.base20.concept03;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Flux与Mono区别的测试用例
 */
public class FluxMonoDifferenceTest {
    
    private FluxMonoDifferenceDemo demo = new FluxMonoDifferenceDemo();
    
    /**
     * 测试Mono的基本用法
     */
    @Test
    void testMonoBasics() {
        // 测试存在的用户
        StepVerifier.create(demo.findUserById("1"))
            .expectNext(new User("1", "John"))
            .verifyComplete();
        
        // 测试不存在的用户
        StepVerifier.create(demo.findUserById("999"))
            .verifyComplete();
    }
    
    /**
     * 测试Flux的基本用法
     */
    @Test
    void testFluxBasics() {
        StepVerifier.create(demo.findUsersByName("John"))
            .expectNext(new User("1", "John"))
            .expectNext(new User("2", "John"))
            .verifyComplete();
    }
    
    /**
     * 测试Mono转Flux
     */
    @Test
    void testMonoToFlux() {
        Mono<User> userMono = demo.findUserById("1");
        Flux<User> userFlux = demo.monoToFlux(userMono);
        
        StepVerifier.create(userFlux)
            .expectNext(new User("1", "John"))
            .verifyComplete();
    }
    
    /**
     * 测试Flux转Mono
     */
    @Test
    void testFluxToMono() {
        Flux<User> userFlux = demo.findUsersByName("John");
        Mono<List<User>> userListMono = demo.fluxToMono(userFlux);
        
        StepVerifier.create(userListMono)
            .expectNext(List.of(
                new User("1", "John"),
                new User("2", "John")
            ))
            .verifyComplete();
    }
    
    /**
     * 测试Mono的错误处理
     */
    @Test
    void testMonoErrorHandling() {
        StepVerifier.create(demo.findUserWithErrorHandling("999"))
            .expectNext(new User("0", "Default"))
            .verifyComplete();
    }
    
    /**
     * 测试Flux的错误处理
     */
    @Test
    void testFluxErrorHandling() {
        StepVerifier.create(demo.findUsersWithErrorHandling("NonExistent"))
            .expectNext(new User("0", "Default"))
            .verifyComplete();
    }
} 