package com.qpzm7903.concurrentdemo.producer_and_consumer_demo;

import java.util.Random;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-29 06:32
 */

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);
        new MakerThread("makeThread-1", new Random(1231), table).start();
        new MakerThread("makeThread-2", new Random(1241), table).start();
        new MakerThread("makeThread-3", new Random(1251), table).start();

        new EnterThread("enterThread-1", new Random(44224), table).start();
        new EnterThread("enterThread-2", new Random(44324), table).start();
        new EnterThread("enterThread-3", new Random(44424), table).start();

    }
}
