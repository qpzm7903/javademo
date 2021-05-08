package com.qpzm7903.concurrentdemo.future_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 20:10
 */

public class Host {
    public Data request(final int count, final char c) {
        System.out.println("    requests(" + count + ", " + c + ") begin");
        final FutureData futureData = new FutureData();
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData(count, c);
                futureData.setRealData(realData);
            }
        }.start();
        System.out.println("    request(" + count + ", " + c + ") end");
        return futureData;
    }
}
