package com.qpzm7903.concurrentdemo.read_wirte_lock;


/**
 * @program: javaDemo
 * @escription: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 07:55
 */

public class Data implements IData{
    private final char[] buffer;
    private final MyReadWriteLock lock = new MyReadWriteLock();

    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

    @Override
    public char[] read() throws InterruptedException {
        lock.readLock();
        try {
            return doRead();
        } finally {
            lock.readUnlock();
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        return newBuffer;
    }

    @Override
    public void write(char c) throws InterruptedException {
        lock.writeLock();
        try {
            doWrite(c);
        } finally {
            lock.writeUnlock();
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
