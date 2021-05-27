package com.qpzm7903.concurrentdemo.read_wirte_lock;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 22:40
 */

public class MainConcurrent {
    public static void main(String[] args) {
        DataConcurrent data = new DataConcurrent(10);
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new WriteThread(data, "ABCDEFG").start();
        new WriteThread(data, "abcdefg").start();
    }
}
