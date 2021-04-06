package com.qpzm7903.concurrentdemo.two_phase_termination;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 22:05
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("man begin");
        try {
            CountupThread t = new CountupThread();
            t.start();

            Thread.sleep(2000);
            System.out.println("main: shutdownRequests");
            t.shutdownRequest();

            System.out.println("Main join");
            t.join();
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        System.out.println("main end");

    }
}
