package com.qpzm7903.concurrentdemo.active_object_demo.using_concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 07:27
 */

public class DisplayClientThread extends Thread {
    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                String string = Thread.currentThread().getName() + " " + i;
                activeObject.displayString(string);
                Thread.sleep(200);

            }
        } catch (RejectedExecutionException e) {
            System.out.println(Thread.currentThread().getName() + " :  " + e);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " : " + e);
        }
    }
}
