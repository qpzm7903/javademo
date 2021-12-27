package com.qpzm7903.java8demo.future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-20:08
 */
class ShopTest {


    private ExecutorService executorService;

    List<Shop> shops = Arrays.asList(new Shop("test1"), new Shop("test2"), new Shop("test3"), new Shop("test4"));

    @BeforeEach
    void init() {

        executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

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

        List<CompletableFuture<String>> produce =
                shops.parallelStream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() +
                        " price is " + shop.calculatePrice("produce"), executorService)).collect(Collectors.toList());
        List<String> collect = produce.stream().map(CompletableFuture::join).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     * cost 8034 ms
     * test1 price is 88.552
     * test2 price is 88.06
     * test3 price is 197.85
     * test4 price is 110.84
     */
    @Test
    void test_with_simple_stream() {
        String product = "testProduct";
        List<String> collect = timer(() -> {
            return shops.stream().map(shop -> shop.getPriceWithCode(product)).map(Quote::parse).map(Discount::applyDiscount).collect(Collectors.toList());
        });
        collect.forEach(System.out::println);
    }

    /**
     * cost 2028 ms
     * test1 price is 158.696
     * test2 price is 111.84
     * test3 price is 112.73599999999999
     * test4 price is 181.55700000000002
     */
    @Test
    void test_with_completableFuture_and_apply_and_compose() {
        String product = "testProduct";
        List<String> timer = timer(() -> {
            List<CompletableFuture<String>> collect = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithCode(product),
                            executorService))
                    .map(future -> future.thenApply(Quote::parse))
                    .map(future -> future.thenCompose(quote -> {
                        return CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executorService);
                    }))
                    .collect(Collectors.toList());
            return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        });
        timer.forEach(System.out::println);

    }

    public <T> T timer(Supplier<T> supplier) {
        long start = System.currentTimeMillis();
        T t = supplier.get();
        long end = System.currentTimeMillis();
        System.out.println("cost " + (end - start) + " ms");
        return t;
    }


}