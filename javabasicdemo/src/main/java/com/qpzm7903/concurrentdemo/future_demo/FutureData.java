package com.qpzm7903.concurrentdemo.future_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 19:55
 */

public class FutureData implements Data {
    private RealData realData = null;
    private boolean ready = false;

    public synchronized void setRealData(RealData realData) {
        if (ready) {
            return;
        }
        this.realData = realData;
        this.ready = true;
        notifyAll();
    }

    @Override
    public synchronized String getContent() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getContent();
    }
}
