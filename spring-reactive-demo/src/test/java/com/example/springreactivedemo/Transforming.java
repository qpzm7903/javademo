package com.example.springreactivedemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-02-21-21:08
 */
public class Transforming {


    @Test
    void test_map() {
        Flux.range(1, 10).map(i -> i * i).subscribe(System.out::println);
    }

    @Test
    void test_index() {
        Flux.range(1, 10).map(i -> i * i).index().subscribe(System.out::println);
    }

    @Test
    void test_flatMap() {
        Flux<Integer> flux = Flux.range(1, 10).flatMap(i -> Flux.range(i,  2));
        flux.subscribe(System.out::println);
    }

    @Test
    void test_collect() {
        List<Integer> block = Flux.range(1, 10).collect(Collectors.toList()).block();
        System.out.println(block);
    }

    @Test
    void test_collect_map() {
        Map<String, String> block = Flux.just("hello", "world", "from", "reactor", "java").collectMap(value -> value.substring(0, 1),
                value -> value.toUpperCase(Locale.ROOT)).block();
        System.out.println(block);

    }

    @Test
    void test_reduce() {
        Integer block = Flux.range(1, 10).reduce(Integer::sum).block();
        System.out.println(block);
    }

    @Test
    void test_all() {
        Boolean block = Flux.range(1, 10).all(i -> i > 0).block();
        System.out.println(block);
    }


    @Test
    void test_concat() {
        Flux.concat(Flux.just(1), Flux.just(2)).subscribe(System.out::println);
    }

    @Test
    void test_zip(){
        Flux<Integer> just = Flux.just(1, 2, 3, 4);
        Flux<String> just1 = Flux.just("a", "b", "c", "d");
        Flux.zip(just, just1).subscribe(System.out::println);
    }

    @Test
    void test_when() {
        Mono<String> a = Mono.just("a");
        Mono<String> b = Mono.just("b");
        a.subscribe();
        b.subscribe(System.out::println);
        // todo what is this？？
        Mono<Void> and = a.and(b);
        and.subscribe(System.out::println);
    }

    @Test
    void test_default_if_empty() {
        Object test = Flux.just().defaultIfEmpty("test").blockFirst();
        System.out.println(test);
    }

    @Test
    void test_switch_if_empty() {
        Object aSwitch = Flux.just().switchIfEmpty(Flux.just("switch")).blockFirst();
        System.out.println(aSwitch);
    }

    @Test
    void test_ignore_elements() {
        Mono<Integer> integerMono = Flux.range(1, 10).ignoreElements();
        integerMono.subscribe(System.out::println);
    }

    @Test
    void test_completion() {
        Mono<Void> then = Flux.range(1, 10).then();

    }

    @Test
    void test_another_mono(){
        String block = Mono.just("test").then(Mono.just("another")).block();
        System.out.println(block);
    }

    @Test
    void test_then_empty() {
    }

}
