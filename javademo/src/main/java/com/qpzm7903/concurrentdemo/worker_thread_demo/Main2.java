package com.qpzm7903.concurrentdemo.worker_thread_demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-01 08:14
 */

public class Main2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        try{

            new ClientThread2("Bobby", service).start();
            new ClientThread2("Alice", service).start();
            new ClientThread2("Chris", service).start();
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            service.shutdown();
        }


    }
}
