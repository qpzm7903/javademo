package com.qpzm7903.java8demo.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-20:08
 */
class ShopTest {


    @Test
    void test() {
        Shop shop = new Shop();
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

        Shop shop = new Shop();
        Future<Double> test = shop.getPrice("");
        try {
            Double aDouble = test.get();
            System.out.println(aDouble);
        } catch (InterruptedException | ExecutionException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
    }
}