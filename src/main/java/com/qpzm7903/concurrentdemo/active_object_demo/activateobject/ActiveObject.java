package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 23:15
 */

public interface ActiveObject {
    public Result<String> makeString(int count, char fillchar);

    public void displayString(String string);
}
