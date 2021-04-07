package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;


/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:20
 */

public abstract class MethodRequest <T>{
    protected final Servant servant;
    protected final FutureResult<String> future;

    protected MethodRequest(Servant servant, FutureResult<String> future) {
        this.servant = servant;
        this.future = future;
    }

    public abstract void execute();
}
