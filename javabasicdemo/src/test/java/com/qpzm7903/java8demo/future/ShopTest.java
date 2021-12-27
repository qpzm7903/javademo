package com.qpzm7903.java8demo.future;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-20:08
 */
class ShopTest {


    @Test
    void test() {
        Shop shop = new Shop("test");
        Future<Double> test = shop.getPrice("test");
        try {
            Double aDouble = test.get();
            System.out.println(aDouble);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_exception() {

        Shop shop = new Shop("test");
        Future<Double> test = shop.getPrice("");
        try {
            Double aDouble = test.get();
            System.out.println(aDouble);
        } catch (InterruptedException | ExecutionException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
    }

    @Test
    void test_list() {
        List<Shop> shops = Arrays.asList(new Shop("test1"), new Shop("test2"), new Shop("test3"), new Shop("test4"));
        List<String> produce = shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.getName(),
                shop.calculatePrice("produce"))).collect(Collectors.toList());

        produce.forEach(System.out::println);

    }

    @Test
    void test_list_2() {
        List<Shop> shops = Arrays.asList(new Shop("test1"), new Shop("test2"), new Shop("test3"), new Shop("test4"));
        List<CompletableFuture<String>> produce =
                shops.parallelStream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() +
                        " price is " + shop.calculatePrice("produce"))).collect(Collectors.toList());
        List<String> collect = produce.stream().map(CompletableFuture::join).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    void test_with_executor() {
        List<Shop> shops = Arrays.asList(new Shop("test1"), new Shop("test2"), new Shop("test3"), new Shop("test4"));
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        List<CompletableFuture<String>> produce =
                shops.parallelStream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() +
                        " price is " + shop.calculatePrice("produce"), executorService)).collect(Collectors.toList());
        List<String> collect = produce.stream().map(CompletableFuture::join).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }


}