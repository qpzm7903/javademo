package com.qpzm7903.concurrentdemo.active_object_demo.using_concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 07:26
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
            for (int i = 0; true; i++) {
                Future<String> stringFuture = activeObject.makeString(i, fillchar);
                Thread.sleep(10);
                String value = stringFuture.get();
                System.out.println(Thread.currentThread().getName() + " : value = " + value);
            }
        } catch (RejectedExecutionException e) {
            System.out.println(Thread.currentThread().getName() + " :  " + e);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " : " + e);
        } catch (ExecutionException e) {
            System.out.println(Thread.currentThread().getName() + " : " + e);
        }
    }
}
