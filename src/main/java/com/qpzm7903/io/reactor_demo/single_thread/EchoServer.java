package com.qpzm7903.io.reactor_demo.single_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-26 23:01
 */

public class EchoServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public EchoServer(Selector selector, ServerSocketChannel serverSocketChannel) throws ClosedChannelException {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptorHandler());

    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("事件数量 " + selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // reactor分发事件
                System.out.println("处理事件");
                SelectionKey selectionKey = iterator.next();
                dispatch(selectionKey);
            }
            selectionKeys.clear();
        }

    }


    private void dispatch(SelectionKey selectionKey) {
        // 获取之前attach的handler
        Runnable handler = (Runnable) selectionKey.attachment();
        if (handler != null) {
            handler.run();
        }
    }

    // 连接处理器
    public class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                System.out.println("接收到一个连接");
                if (channel != null) {
                    // 收到连接，并处理连接
                    new EchoHandler(selector, channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9090));

        new Thread(new EchoServer(selector, serverSocketChannel)).start();

    }
}
