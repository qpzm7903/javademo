package com.qpzm7903.io.nio_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-23 08:07
 */

public class FileChannelDemo {
    public static void main(String[] args) throws FileNotFoundException {
        // 获取文件输入流
        FileInputStream fis = new FileInputStream(new File("test.txt"));
        FileChannel intPutChannel = fis.getChannel();

        // 输出文件流
        FileOutputStream fileOutputStream = new FileOutputStream("test-bak.txt");
        FileChannel outPuChannel = fileOutputStream.getChannel();

    }
}
