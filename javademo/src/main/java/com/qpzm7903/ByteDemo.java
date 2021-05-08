package com.qpzm7903;

import java.nio.charset.StandardCharsets;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-07 22:28
 */

public class ByteDemo
{
    public static void main(String[] args) {
        String s = "/:;";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }
}
