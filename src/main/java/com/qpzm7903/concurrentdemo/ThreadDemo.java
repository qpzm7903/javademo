package com.qpzm7903.concurrentdemo;

/**
 * @program: javaDemo
 * @description: 验证空的thread
 * @author: qpzm7903
 * @create: 2021-03-26 21:36
 */

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
    }
}
