package com.qpzm7903.io.nio_demo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-23 08:07
 */

public class FileChannelReadFileDemo {
    public static void main(String[] args) throws IOException {
        readFile("test.txt");
    }

    public static String readFile(String fileName) throws IOException {
        // 获取文件输入流
        FileInputStream fis = new FileInputStream(new File(fileName));
        FileChannel intPutChannel = fis.getChannel();
        StringBuffer stringBuffer = new StringBuffer();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        // 通道是读模式， 但是buffer是写模式

        while (intPutChannel.read(buffer) != -1) {
            buffer.flip();
            // 想要从buffer里面获取数据，需要flip切换模式
            while (buffer.position() != buffer.limit()) {
                // 将byte转为char
                stringBuffer.append((char) buffer.get());
            }
            // 反转为读模式
//            buffer.clear();
            buffer.flip();
        }
        String result = stringBuffer.toString();
        System.out.println(result);
        return result;

    }
}
