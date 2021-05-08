package com.qpzm7903.io.reactor_demo.single_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-26 23:02
 */
public class EchoClient {
    private SocketChannel socketChannel;

    EchoClient(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void start() throws IOException {
        Processor processor = new Processor(socketChannel);
        new Thread(processor).start();
    }


    class Processor implements Runnable {


        private SocketChannel socketChannel;

        private Selector selector;

        public Processor(SocketChannel socketChannel) throws IOException {


            this.socketChannel = socketChannel;
            this.selector = Selector.open();
            this.socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        @Override
        public void run() {

            try {
                while (!Thread.interrupted()) {
                    //  选择，并阻塞直到有channel可用
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        if (next.isWritable()) {
                            read_and_write_to_channel(next);
                        }
                        if (next.isReadable()) {
                            read_from_channel_and_echo(next);

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void read_from_channel_and_echo(SelectionKey selectionKey) throws IOException {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length;
            while ((length = channel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                System.out.println("server echo:" + new String(byteBuffer.array(), 0, length));
                byteBuffer.clear();
            }
        }

        private void read_and_write_to_channel(SelectionKey selectionKey) throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            Scanner scanner = new Scanner(System.in);
            System.out.println("input content");
            if (scanner.hasNext()) {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                String content = scanner.next();
                byteBuffer.put(content.getBytes(StandardCharsets.UTF_8));
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 9090));
        while (!socketChannel.finishConnect()) {
            System.out.println("connecting");
        }
        new EchoClient(socketChannel).start();
    }

}
