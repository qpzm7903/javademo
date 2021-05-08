package com.qpzm7903.concurrentdemo.read_wirte_lock;

public interface IData {
    char[] read() throws InterruptedException;

    void write(char c) throws InterruptedException;
}
