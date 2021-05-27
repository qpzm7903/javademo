package com.qpzm7903.concurrentdemo.thread_specific_storage;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-07 22:34
 */

public class Log {

    private static final ThreadLocal<TSLog> tsLogCollection = new ThreadLocal<>();

    public static void println(String s) {
        getTSLog().println(s);
    }

    public static void close(){
        getTSLog().close();
    }

    private static TSLog getTSLog() {
        TSLog tsLog = tsLogCollection.get();
        if (tsLog == null) {
            tsLog =  new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollection.set(tsLog);

        }
        return tsLog;
    }
}
