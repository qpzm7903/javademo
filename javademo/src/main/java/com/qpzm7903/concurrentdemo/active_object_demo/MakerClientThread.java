package com.qpzm7903.concurrentdemo.active_object_demo;

import com.qpzm7903.concurrentdemo.active_object_demo.activateobject.ActiveObject;
import com.qpzm7903.concurrentdemo.active_object_demo.activateobject.Result;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 06:46
 */

public class MakerClientThread extends Thread {
    private final ActiveObject activeObject;
    private final char fillchar;

    public MakerClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Result<String> result = activeObject.makeString(i, fillchar);
                Thread.sleep(100);
                String value = result.getResultValue();
                System.out.println(Thread.currentThread().getName() + ": value = " + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
