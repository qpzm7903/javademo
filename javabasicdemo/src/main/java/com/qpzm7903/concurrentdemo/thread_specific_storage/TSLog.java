package com.qpzm7903.concurrentdemo.thread_specific_storage;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 22:32
 */

public class TSLog {
    private PrintWriter writer = null;

    public TSLog(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void println(String s) {
        writer.println(s);
    }

    public void close() {
        writer.println("===== end of log ====");
        writer.close();
    }
}
