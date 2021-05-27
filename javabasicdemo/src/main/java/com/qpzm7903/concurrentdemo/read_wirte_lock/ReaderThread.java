package com.qpzm7903.concurrentdemo.read_wirte_lock;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 08:11
 */

public class ReaderThread extends Thread {
    private final IData data;

    public ReaderThread(IData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try{
            while (true) {
                char[] readBuf = data.read();
                System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(readBuf));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
