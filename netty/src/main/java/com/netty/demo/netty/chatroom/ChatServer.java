package com.netty.demo.netty.chatroom;

import com.netty.demo.netty.chatroom.handler.common.MyFrameDecoder;
import com.netty.demo.netty.chatroom.handler.server.*;
import com.netty.demo.netty.chatroom.protocal.LTPCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatServer {
    public static void main(String[] args) {
        // 负责建立连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 负责处理读写
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LTPCodec LTPCODEC = new LTPCodec();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        ChatRequestHandler CHAT_REQUEST = new ChatRequestHandler();
        GroupCreateRequestHandler GROUP_CREATE_HANDLER = new GroupCreateRequestHandler();
        GroupChatHandler GROUP_CHAT_HANDLER =  new GroupChatHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();
        UserEventHandler USER_EVENT_HANDLER = new UserEventHandler();
        PingMessageHandler PINGMESSAGEHANDLER = new PingMessageHandler();
        try {
            Channel channel = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(5,0,0));
                            pipeline.addLast(USER_EVENT_HANDLER);
                            pipeline.addLast(LOGGINGHANDLER);
                            pipeline.addLast(new MyFrameDecoder());
                            pipeline.addLast(LTPCODEC);
                            pipeline.addLast(new LoginHandler());
                            pipeline.addLast(CHAT_REQUEST);
                            pipeline.addLast(GROUP_CREATE_HANDLER);
                            pipeline.addLast(GROUP_CHAT_HANDLER);
                            pipeline.addLast(QUIT_HANDLER);
                            pipeline.addLast(PINGMESSAGEHANDLER);
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
