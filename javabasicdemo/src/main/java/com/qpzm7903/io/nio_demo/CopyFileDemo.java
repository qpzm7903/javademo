package com.qpzm7903.io.nio_demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.qpzm7903.io.nio_demo.FileChannelReadFileDemo.readFile;
import static com.qpzm7903.io.nio_demo.FileChannelWriteDemo.writeFile;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-24 18:25
 */

public class CopyFileDemo {
    public static void main(String[] args) throws IOException {
        String content = readFile("test.txt");
        writeFile("test_copy.txt", content);
        String readContent = readFile("test_copy.txt");
        assert content.equals(readContent);

        copyFile("test.txt", "test_copy2.txt");
        String content2 = readFile("test_copy2.txt");
        assert content.equals(content2);
    }


    public static void copyFile(String src, String dist) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileOutputStream fileOutputStream = new FileOutputStream(dist);
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inputStreamChannel.read(buffer) != -1) {
            buffer.flip();
            while (outputStreamChannel.write(buffer) != 0) {

            }
            buffer.clear();
        }
        // 强制写入硬盘
        outputStreamChannel.force(true);
        outputStreamChannel.close();
        fileOutputStream.close();
        inputStreamChannel.close();
        fileInputStream.close();
    }
}
