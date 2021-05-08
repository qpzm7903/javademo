package com.qpzm7903.concurrentdemo.producer_and_consumer_demo;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-29 06:35
 */

public class EnterThread extends Thread {
    private final Random random;
    private final Table table;

    public EnterThread(String name, Random random, Table table) {
        super(name);
        this.random = random;
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String cake = table.take();
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
