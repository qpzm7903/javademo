package com.qpzm7903.concurrentdemo.worker_thread_demo;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-31 21:18
 */

public class Request {
    private final String name;
    private final int number;
    private static final Random random = new Random();
    public Request(String name, int i) {
        this.name = name;
        this.number = i;
    }
    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executes " + this);
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
