package com.qpzm7903.concurrentdemo.active_object_demo.using_concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 07:21
 */

public class ActiveObjectImpl implements ActiveObject {
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    @Override
    public Future<String> makeString(int count, char fillchar) {
        class MakeStringRequest implements Callable<String> {

            @Override
            public String call() throws Exception {
                char[] buffer = new char[count];
                for (int i = 0; i < count; i++) {
                    buffer[i] = fillchar;
                    Thread.sleep(100);
                }
                return new String(buffer);
            }
        }
        return service.submit(new MakeStringRequest());
    }

    @Override
    public void displayString(String string) {
        class DisplayStringRequest implements Runnable{
            @Override
            public void run() {
                System.out.println("displayString: " + string);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        service.execute(new DisplayStringRequest());
    }

    @Override
    public void shutdown() {
        service.shutdown();

    }
}
