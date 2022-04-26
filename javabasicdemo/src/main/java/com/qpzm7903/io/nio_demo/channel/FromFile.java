package com.qpzm7903.io.nio_demo.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从文件流获取channel ，并且通过buffer读取的简单例子
 *
 * @author qpzm7903
 * @since 2022-04-26-6:59
 */
public class FromFile {


    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try (RandomAccessFile file = new RandomAccessFile("data/test.txt", "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(24);
            int read = channel.read(buffer);
            while (read != -1) {
                // change to read mode
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                // change to write mode
                buffer.clear();
                read = channel.read(buffer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
