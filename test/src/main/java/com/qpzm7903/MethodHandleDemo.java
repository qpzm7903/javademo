package com.qpzm7903;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-09-11-21:59
 */
public class MethodHandleDemo {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");

            }
        };
        r1.run();
    }
}
