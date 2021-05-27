package com.qpzm7903.concurrentdemo.future_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 20:12
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main begin");
        Host host = new Host();
        Data data1 = host.request(10, 'A');
        Data data2 = host.request(30, 'B');
        Data data3 = host.request(220, 'C');

        System.out.println("main other job begin");
        Thread.sleep(2000);
        System.out.println("main other job end");

        System.out.println("data1 =" + data1.getContent());
        System.out.println("data2 =" + data2.getContent());
        System.out.println("data3 =" + data3.getContent());

    }
}
