package com.ipass.hellonetty;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.ResourceLeakDetector;

import java.net.InetSocketAddress;

/**
 * Created by dshively on 7/6/15.
 */
public class HelloNetty {

    static {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
    }

    public static void main(String[] args) throws Exception {

        if (Epoll.isAvailable()) {
            doRun(new EpollEventLoopGroup(), EpollServerSocketChannel.class);
        } else {
            doRun(new NioEventLoopGroup(), NioServerSocketChannel.class);
        }

    }

    private static void doRun(EventLoopGroup loupGroup, Class<? extends ServerChannel> serverChannelClass) throws InterruptedException {
        try {
            InetSocketAddress inet = new InetSocketAddress(8080);

            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.option(ChannelOption.SO_REUSEADDR, true);
            b.option(ChannelOption.SO_RCVBUF, 1048000);
            b.option(ChannelOption.SO_SNDBUF, 1048000);
            b.group(loupGroup).channel(serverChannelClass).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast("encoder", new HttpResponseEncoder());
                    p.addLast("decoder", new HttpRequestDecoder(4096, 8192, 8192, false));
                    p.addLast(new HttpObjectAggregator(1048576));
//                    p.addLast(new HelloNettyDecoder());
                    p.addLast(new HelloNettyHandler());
                }
            });
            b.option(ChannelOption.MAX_MESSAGES_PER_READ, Integer.MAX_VALUE);
            b.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(true));
            b.childOption(ChannelOption.SO_REUSEADDR, true);
            b.childOption(ChannelOption.MAX_MESSAGES_PER_READ, Integer.MAX_VALUE);

            Channel ch = b.bind(inet).sync().channel();

            System.out.printf("Httpd started. Listening on: %s%n", inet.toString());

            ch.closeFuture().sync();
        } finally {
            loupGroup.shutdownGracefully().sync();
        }
    }
}

