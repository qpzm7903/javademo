package com.qpzm7903.java8demo.future;

import java.util.Random;

import static com.qpzm7903.java8demo.future.Shop.delay;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-21:40
 */
public class ExchangeService {
    public static double getRate(Money from, Money to) {
        delay();
        double i = new Random().nextInt(100);
        return i / 100;
    }
}
