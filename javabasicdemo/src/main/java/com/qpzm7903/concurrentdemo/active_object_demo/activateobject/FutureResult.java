package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:12
 */

public class FutureResult<T> extends Result<T> {
    private Result<T> result;
    private boolean ready = false;

    public synchronized void setResult(Result<T> result) {
        this.result = result;
        ready = true;
        notifyAll();
    }

    public synchronized T getResultValue() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.getResultValue();
    }

}
