package com.qpzm7903.concurrentdemo.guardedsuspension;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-28 10:14
 */

public class Main {
    public static void main(String[] args) {
        RequestsQueue requestsQueue = new RequestsQueue();
        new ClientThread(new Random(34343L), requestsQueue, "client").start();
        new ServerThread(new Random(34343L), requestsQueue, "server").start();
    }
}
