package com.qpzm7903.concurrentdemo.active_object_demo.using_concurrent;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-08 07:26
 */

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        try {
            new MakerClientThread("alice", activeObject).start();
            new MakerClientThread("bobby", activeObject).start();
            new DisplayClientThread("chris", activeObject).start();
            Thread.sleep(5000);

        } catch (InterruptedException e) {

        } finally {
            System.out.println(" **** shutdown ****");
            activeObject.shutdown();
        }
    }
}
