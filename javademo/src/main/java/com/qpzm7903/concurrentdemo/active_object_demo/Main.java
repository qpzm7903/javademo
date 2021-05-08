package com.qpzm7903.concurrentdemo.active_object_demo;

import com.qpzm7903.concurrentdemo.active_object_demo.activateobject.ActiveObject;
import com.qpzm7903.concurrentdemo.active_object_demo.activateobject.ActiveObjectFactory;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 06:45
 */

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice",activeObject).start();
        new MakerClientThread("Bobby",activeObject).start();
        new DisplayClientThread("Chris",activeObject).start();
    }
}
