package com.qpzm7903.concurrentdemo.thread_specific_storage;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 22:39
 */

public class Main {
    public static void main(String[] args) {
        new ClientThread(("Alice")).start();
        new ClientThread("bobby").start();
        new ClientThread("chris").start();

    }
}
