package com.qpzm7903.concurrentdemo.two_phase_termination.count_down_latch_demo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-06 22:04
 */

public class Task implements Runnable {
    private final CountDownLatch doneLatch;
    private final int context;
    private static final Random random = new Random(314159);

    public Task(CountDownLatch doneLatch, int context) {
        this.doneLatch = doneLatch;
        this.context = context;
    }

    @Override
    public void run() {
        doTask();
        doneLatch.countDown();
    }

    protected void doTask() {
        System.out.println(Thread.currentThread().getName() + ":Task:begin:context=" + context);
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":Task:end:context=" + context);
        }

    }
}
