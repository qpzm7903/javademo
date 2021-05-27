package com.qpzm7903.concurrentdemo.read_wirte_lock;

import java.util.TreeMap;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-03-30 07:57
 */

public class MyReadWriteLock {
    private int readingReaders; // count of reading thread
    private int waitingWriters; // count of waiting for write thread
    private int writingWriters; // count of writing thread
    private boolean preferWriter = true; // if prefer, writer priority first

    public synchronized void readLock() throws InterruptedException {
        while (writingWriters > 0 || (preferWriter && waitingWriters > 0)) {
            wait();
        }
        readingReaders ++;
    }

    public synchronized void readUnlock(){
        readingReaders--;
        preferWriter = true;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWriters ++ ;  // first waiting for write
        try{
            while (readingReaders > 0 || writingWriters > 0) {
                wait();
            }
        }finally {
            waitingWriters--;
        }
        writingWriters++;
    }

    public synchronized  void writeUnlock(){
        writingWriters--;
        preferWriter = false;
        notifyAll();
    }
}
