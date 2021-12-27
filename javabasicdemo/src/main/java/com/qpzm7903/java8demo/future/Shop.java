package com.qpzm7903.java8demo.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-19:55
 */
public class Shop {
    private String name;
    private final static Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public Future<Double> getPrice(String product) {
        return CompletableFuture.supplyAsync(()->calculatePrice(product));
    }

    public String getPriceWithCode(String product){
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
