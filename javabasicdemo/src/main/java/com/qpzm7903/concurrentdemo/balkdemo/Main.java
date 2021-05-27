package com.qpzm7903.concurrentdemo.balkdemo;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-28 22:33
 */

public class Main {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "empty");
        new ChangerThread("changerThread",data).start();
        new SaverThread("saverThread", data).start();
    }
}
