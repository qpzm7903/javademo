package com.qpzm7903.concurrentdemo.worker_thread_demo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-31 21:42
 */

public class WorkerThread extends Thread {
    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {

            Request request = channel.takeRequest();
            request.run();
        }
    }
}
