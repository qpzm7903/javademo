package com.qpzm7903.concurrentdemo.read_wirte_lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 22:40
 */

public class DataConcurrent  implements IData{

    private final char[] buffer;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public DataConcurrent(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

    @Override
    public char[] read() throws InterruptedException {
        lock.readLock().lock();
        try {
            return doRead();
        } finally {
            lock.readLock().unlock();
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        return newBuffer;
    }

    @Override
    public void write(char c) throws InterruptedException {
        lock.writeLock().lock();
        try {
            doWrite(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();

        }
    }

    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
