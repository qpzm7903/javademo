package com.qpzm7903;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.*;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2022-04-26-18:20
 */
public class SelectorDemo {

    @Test
    void test_open() throws IOException {
        Selector open = Selector.open();
    }

    @Test
    void test_registry() throws IOException {

        /**
         * channel must be nonblocking
         */
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress("localhost", 9090));
        Selector selector = Selector.open();
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ & SelectionKey.OP_ACCEPT);

        int interestOps = selectionKey.interestOps();

        int readyOps = selectionKey.readyOps();

        SelectableChannel channelFromSk = selectionKey.channel();
        Selector selectorFromSk = selectionKey.selector();

        assert  channelFromSk == channel;
        assert  selectorFromSk == selector;

        int select = selector.select(1000);
        if (select == 0) {
            System.out.println("select nothing");
            System.out.println(selector.selectedKeys().size());
        }



    }
}
