package com.qpzm7903.concurrentdemo.producer_and_consumer_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-29 06:33
 */

public class Table {
    private final String[] buffer;
    private int tail; // next time put position
    private int head; // next time take position
    private int count; // count of cake in buffer

    public Table(int count) {
        this.count = 0;
        this.head = 0;
        this.tail = 0;
        this.buffer = new String[count];
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
            System.out.println(Thread.currentThread().getName() + " wait ");
        }
        String cake = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " takes " + cake);
        return cake;
    }

    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts " + cake);
        while (count >= buffer.length) {
            wait();
            System.out.println(Thread.currentThread().getName() + " wait ");

        }
        buffer[tail] = cake;
        tail = (tail + 1) % buffer.length;
        count++;
        notifyAll();
    }
}
