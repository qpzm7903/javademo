package com.qpzm7903.concurrentdemo.two_phase_termination.count_down_latch_demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 22:21
 */

public class Main {
    private static final int TASKS = 10;

    public static void main(String[] args) {
        System.out.println("begin");
        CountDownLatch countDownLatch = new CountDownLatch(TASKS);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < TASKS; i++) {
                executorService.execute(new Task(countDownLatch, i));
            }
            System.out.println("await");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            executorService.shutdown();

            System.out.println("end");
        }
    }
}
