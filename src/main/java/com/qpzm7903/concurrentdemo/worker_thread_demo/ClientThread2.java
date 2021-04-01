package com.qpzm7903.concurrentdemo.worker_thread_demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-01 08:17
 */

public class ClientThread2 extends Thread {

    private final ExecutorService service;
    private static final Random random = new Random();

    public ClientThread2(String name, ExecutorService service) {
        super(name);
        this.service = service;
    }

    @Override
    public void run() {
        try {

            for (int i = 0; true; i++) {

                Request request = new Request(getName(), i);
                service.execute(request);
                Thread.sleep(random.nextInt(1000));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RejectedExecutionException e) {
            System.out.println(getName() + " : " + e);
        }

    }
}
