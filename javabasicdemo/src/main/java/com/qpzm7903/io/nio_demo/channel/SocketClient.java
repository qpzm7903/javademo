package com.qpzm7903.io.nio_demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Time;

/**
 * 简易客户端
 *
 * @author qpzm7903
 * @since 2021-04-25 20:07
 */

public class SocketClient {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    connect(finalI);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private static void connect(int i) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 9090));
        while (!socketChannel.finishConnect()) {

        }
        System.out.println("connected" + i);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        String message = "hello from nio" + i;
        byteBuffer.put(message.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }
}
