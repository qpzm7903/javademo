package com.qpzm7903.concurrentdemo.guardedsuspension;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-28 10:14
 */

public class ServerThread extends Thread {
    private final Random random;
    private final RequestsQueue requestsQueue;

    public ServerThread(Random random, RequestsQueue requestsQueue, String name) {
        super(name);
        this.random = random;
        this.requestsQueue = requestsQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Request request = requestsQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles " + request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
