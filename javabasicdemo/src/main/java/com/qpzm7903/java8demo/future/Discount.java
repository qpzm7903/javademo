package com.qpzm7903.java8demo.future;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-12-27-21:16
 */
public class Discount {
    public enum Code {
        NODE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code discountCode) {
        delay();
        return price * (100 - discountCode.percentage) / 100;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
