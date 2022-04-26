package com.qpzm7903.io.nio_demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 简易服务器
 *
 * @author qpzm7903
 * @since 2021-04-25 20:07
 * @since 2022-04-26
 */

public class SocketServer {
    public static void main(String[] args) throws IOException {


        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9090));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // see java doc , selector.select() will block util at least one channel is selected

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // one selector can register with many channel
                    socketChannel.register(selector, SelectionKey.OP_READ);


                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = channel.read(buffer)) > 0) {
                        // change to read mode
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, length));
                        // change to write mode
                        buffer.clear();
                    }
                    channel.close();
                }
                // why?
                iterator.remove();

            }
        }
    }
}