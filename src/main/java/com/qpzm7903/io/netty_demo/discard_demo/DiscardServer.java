package com.qpzm7903.io.netty_demo.discard_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-05-01 21:46
 */

@Slf4j
public class DiscardServer {


    public static final int INET_PORT = 9090;
    ServerBootstrap serverBootstrap;

    public DiscardServer() {
        this.serverBootstrap = new ServerBootstrap();
    }

    public void runServer() {
        // 创建reactor 线程组
        EventLoopGroup boosLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup(1);

        try {

            // 设置reactor线程组
            serverBootstrap.group(boosLoopGroup, workerLoopGroup);
            // 设置nio类型的channel
            serverBootstrap.channel(NioServerSocketChannel.class);

            // 设置监听端口
            serverBootstrap.localAddress(INET_PORT);

            // 设置通道参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 装配子通道流水线,父通道的逻辑是固定的，由netty完成
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardHandler());
                }
            });
            // 异步任务
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            log.info("服务器启动成功，监听端口：" + channelFuture.channel().localAddress());


            // 等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
            System.out.println("关闭的时候才会打印");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerLoopGroup.shutdownGracefully();
            boosLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new DiscardServer().runServer();

    }
}
