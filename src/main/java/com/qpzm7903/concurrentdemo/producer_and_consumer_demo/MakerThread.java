package com.qpzm7903.concurrentdemo.producer_and_consumer_demo;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-29 06:32
 */

public class MakerThread extends Thread {
    private final Random random;
    private final Table table;
    private static int id = 0;

    public MakerThread(String name, Random random, Table table) {
        super(name);
        this.random = random;
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (true) {

                Thread.sleep(random.nextInt(1000));

                String cake = "[ Cake No." + nextId() + " by " + getName() + " ]";

                table.put(cake);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized int nextId() {
        return id++;
    }
}
