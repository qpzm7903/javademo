package com.qpzm7903.io.nio_demo;

import java.nio.IntBuffer;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-21 21:01
 */

public class IntBufferDemo {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(20);
        showStatus(intBuffer, "after init");
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        showStatus(intBuffer, "after put");
        intBuffer.flip();
        showStatus(intBuffer, "after flip");
        while (intBuffer.position() != intBuffer.limit()) {
            System.out.println(intBuffer.get());
        }
        showStatus(intBuffer, "after get");
        intBuffer.rewind();
        showStatus(intBuffer, "after rewind");
        while (intBuffer.position() != intBuffer.limit()) {
            System.out.println(intBuffer.get());
        }
        showStatus(intBuffer, "after rewind and get");

        System.out.println(intBuffer);

    }

    private static void showStatus(IntBuffer intBuffer, String action) {

        System.out.println("status after " + action);
        System.out.println("position:" + intBuffer.position());
        System.out.println("limit:" + intBuffer.limit());
        System.out.println("capacity:" + intBuffer.capacity());
    }
}
