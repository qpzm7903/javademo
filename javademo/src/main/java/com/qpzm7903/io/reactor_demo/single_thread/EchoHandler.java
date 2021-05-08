package com.qpzm7903.io.reactor_demo.single_thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-27 07:34
 */

public class EchoHandler implements Runnable {

    private Selector selector;
    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECEIVING = 0;
    static final int SENDING = 1;
    int state = RECEIVING;

    public EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        System.out.println("create a echo handler");
        this.selector = selector;
        this.socketChannel = socketChannel;

        this.socketChannel.configureBlocking(false);
        selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        //
        selectionKey.attach(this);

        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == SENDING) {

                socketChannel.write(byteBuffer);
                byteBuffer.clear();

                selectionKey.interestOps(SelectionKey.OP_READ);

                state = RECEIVING;

            } else if (state == RECEIVING) {
                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
            // 不关闭 selectionKey 因为要重复使用

        } catch (IOException ex) {
            ex.printStackTrace();
            selectionKey.cancel();
            try {
                socketChannel.finishConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(SelectionKey.OP_ACCEPT);
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_WRITE);
        System.out.println(SelectionKey.OP_CONNECT);

    }
}
