package com.qpzm7903.concurrentdemo.start_demo;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printTimeAndThreadId("runnable ");
            }
        });
        thread.start();
        printTimeAndThreadId("main ");

    }

    private static void printTimeAndThreadId(String name) {
        System.out.println(name + "thread id is " + Thread.currentThread().getId() +  " at " + System.currentTimeMillis());
    }

}
