package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:18
 */

public class SchedulerThread extends Thread {
    private final ActivationQueue queue;

    public SchedulerThread(ActivationQueue queue) {
        this.queue = queue;
    }

    public void invoke(MethodRequest<?> request) {
        queue.putRequest(request);
    }

    public void run() {
        while (true) {
            MethodRequest<?> request = queue.takeRequest();
            request.execute();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
