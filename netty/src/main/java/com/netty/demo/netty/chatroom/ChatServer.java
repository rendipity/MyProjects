package com.netty.demo.netty.chatroom;

import com.netty.demo.netty.chatroom.handler.server.ChatRequestHandler;
import com.netty.demo.netty.chatroom.handler.server.LoginHandler;
import com.netty.demo.netty.chatroom.protocal.LTPCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class ChatServer {
    public static void main(String[] args) {
        // 负责建立连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 负责处理读写
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LTPCodec LTPCODEC = new LTPCodec();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        ChatRequestHandler CHAT_REQUEST = new ChatRequestHandler();
        try {
            Channel channel = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(LOGGINGHANDLER);
                            pipeline.addLast(LTPCODEC);
                            pipeline.addLast(new LoginHandler());
                            pipeline.addLast(CHAT_REQUEST);
                        }
                    })
                    // 主线程同步等待连接建立完成
                    .bind(8080).sync().channel();
            // 主线同步等待channel关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}