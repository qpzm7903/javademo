package com.qpzm7903.io.nio_demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-24 11:58
 */

public class FileChannelWriteDemo {
    public static void main(String[] args) throws IOException {
        writeFile("test_write.txt", "test in input");
    }

    public static void writeFile(String fileName, String content) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        FileChannel outPuChannel = fileOutputStream.getChannel();
        byte[] bytes = content.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        outPuChannel.write(byteBuffer);
        outPuChannel.close();
        fileOutputStream.close();
    }
}
