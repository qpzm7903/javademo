package com.qpzm7903.concurrentdemo.active_object_demo.activateobject;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 06:26
 */

public class Servant implements ActiveObject {

    @Override
    public Result<String> makeString(int count, char fillchar) {
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = fillchar;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult<>(new String(buffer));
    }

    @Override
    public void displayString(String string) {
        try {
            System.out.println("displayString :" + string);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
