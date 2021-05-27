package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:16
 */

public class ActiveObjectFactory {
    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        Proxy proxy = new Proxy(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }
}
