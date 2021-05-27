package com.qpzm7903.concurrentdemo;

public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("first time into run " + Thread.currentThread().getState());
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000);
                        System.out.println("sleep 1 seconds");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread end");
                System.out.println("after interrupt " + Thread.currentThread().getState());

            }
        };
        Thread thread = new Thread(runnable);
        System.out.println("after new " + thread.getState());
        thread.start();
        System.out.println("after start " + thread.getState());
        Thread.sleep(3000);
        System.out.println("after main thread sleep 3 s " + thread.getState());
        thread.interrupt();
        System.out.println("after main thread invoke thread.interrupt " + thread.getState());

    }
}
