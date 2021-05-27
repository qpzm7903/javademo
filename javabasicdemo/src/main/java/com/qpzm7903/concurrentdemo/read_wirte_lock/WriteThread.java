package com.qpzm7903.concurrentdemo.read_wirte_lock;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 08:09
 */

public class WriteThread extends Thread {
    private static final Random random = new Random();
    private final IData data;
    private final String filler;
    private int index = 0;

    public WriteThread(IData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try{
            while (index<filler.length()) {
                char c = nextChar();
                data.write(c);
                Thread.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index++;
        return c;
    }
}
