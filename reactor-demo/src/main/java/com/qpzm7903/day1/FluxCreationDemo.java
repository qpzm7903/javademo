package com.qpzm7903.day1;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class FluxCreationDemo {
    
    // 使用just创建固定元素的Flux
    public Flux<String> createFluxFromJust() {
        return Flux.just("Hello", "Reactor", "World");
    }
    
    // 使用fromIterable从集合创建Flux
    public Flux<Integer> createFluxFromIterable() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        return Flux.fromIterable(numbers);
    }
    
    // 使用generate创建动态生成的Flux
    public Flux<Integer> createFluxFromGenerate() {
        return Flux.generate(
            () -> 0, // initial state
            (state, sink) -> {
                sink.next(state); // emit current state
                if (state == 9) {
                    sink.complete(); // complete if reached 10 elements
                }
                return state + 1; // update state
            }
        );
    }
    
    // 创建空流示例
    public Mono<String> createEmptyMono() {
        return Mono.empty();
    }
} 