package com.qpzm7903.concurrentdemo.read_wirte_lock;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 08:13
 */

public class Main {
    public static void main(String[] args) {

        Data data = new Data(10);
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new WriteThread(data, "ABCDEFG").start();
        new WriteThread(data, "abcdefg").start();
    }

}
