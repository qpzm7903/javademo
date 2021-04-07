package com.qpzm7903.concurrentdemo.thread_specific_storage;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 22:37
 */

public class ClientThread extends Thread {
    public ClientThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(getName() + " begin");
        for (int i = 0; i < 10; i++) {
            Log.println("i = " + i);
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.close();
        System.out.println(getName() + " end");

    }
}
