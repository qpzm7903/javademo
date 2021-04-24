package com.qpzm7903.io.nio_demo;

import java.io.IOException;

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
    }
}
