package com.qpzm7903.concurrentdemo.future_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-02 19:56
 */

public class RealData implements Data {
    private final String content;

    public RealData(int count, char c) {
        System.out.println("    making realData(" + count + ", " + c + ") begin");
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = c;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("    making realData(" + count + ", " + c + ") end");
        this.content = new String(buffer);
    }

    @Override
    public String getContent() {
        return content;
    }
}
