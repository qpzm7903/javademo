package com.qpzm7903.io.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-25 20:07
 */

public class SocketClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 9090));
        while (!socketChannel.finishConnect()) {

        }
        System.out.println("connected");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        byteBuffer.put("hello from nio".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();


    }
}
