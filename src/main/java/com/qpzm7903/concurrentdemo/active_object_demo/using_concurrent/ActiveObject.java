package com.qpzm7903.concurrentdemo.active_object_demo.using_concurrent;

import java.util.concurrent.Future;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 07:19
 */

public interface ActiveObject {
    Future<String> makeString(int count, char fillchar);

    void displayString(String string);

    void shutdown();

}
