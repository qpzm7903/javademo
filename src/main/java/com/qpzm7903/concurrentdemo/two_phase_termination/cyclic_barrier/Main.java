package com.qpzm7903.concurrentdemo.two_phase_termination.cyclic_barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-06 22:12
 */

public class Main {
    private static final int THREADS = 3;

    public static void main(String[] args) {
        System.out.println("begin");
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        Runnable barrierAction = new Runnable() {
            @Override
            public void run() {
                System.out.println("Barrier Action");
            }
        };
        CyclicBarrier phaseBarrier = new CyclicBarrier(THREADS, barrierAction);
        CountDownLatch downLatch = new CountDownLatch(THREADS);
        try {
            for (int i = 0; i < THREADS; i++) {
                executorService.execute(new Task(phaseBarrier, downLatch, i));
            }
            System.out.println("await");
            downLatch.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
            System.out.println("end");

        }
    }
}
