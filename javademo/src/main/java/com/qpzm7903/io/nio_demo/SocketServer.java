package com.qpzm7903.io.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-25 20:07
 */

public class SocketServer {
    public static void main(String[] args) throws IOException {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9090));
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = 0;
        while (channel.isConnected() && (length = channel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, length));
            byteBuffer.clear();
        }
        channel.close();
        serverSocketChannel.close();
    }
}