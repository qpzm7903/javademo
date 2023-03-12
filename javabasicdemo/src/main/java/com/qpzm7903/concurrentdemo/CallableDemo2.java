package com.qpzm7903.concurrentdemo;

import java.util.concurrent.*;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-09-18-22:24
 */
public class CallableDemo2 {
    public static void main(String[] args) throws Exception {
        Callable<String> callable = ()-> "hello " + Thread.currentThread().getName() +" " + System.nanoTime();

        String call = callable.call();
        System.out.println(call);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> submit = executorService.submit(callable);
        String result = submit.get();
        System.out.println(result);
        boolean shutdown = executorService.isShutdown();
        System.out.println(shutdown);

//        executorService.shutdown();
    }
}
