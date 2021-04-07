package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:17
 */

public class Proxy implements ActiveObject {

    private SchedulerThread schedulerThread;
    private Servant servant;

    public Proxy(SchedulerThread schedulerThread, Servant servant) {

        this.schedulerThread = schedulerThread;
        this.servant = servant;
    }

    @Override
    public Result<String> makeString(int count, char fillchar) {
        FutureResult<String> future = new FutureResult<>();
        schedulerThread.invoke(new MakeStringRequest(servant, future, count, fillchar));
        return future;
    }

    @Override
    public void displayString(String string) {
        schedulerThread.invoke(new DisplayStringRequest(servant, string));
    }
}
