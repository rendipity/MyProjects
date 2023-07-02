package com.netty.demo.netty.chatroom;

import com.netty.demo.netty.chatroom.handler.client.UserHandler;
import com.netty.demo.netty.chatroom.protocal.LTPCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        String URI = "localhost";
        int PORT = 8080;
        NioEventLoopGroup work = new NioEventLoopGroup();
        LTPCodec LTPCODEC = new LTPCodec();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        try {
            Channel channel = new Bootstrap()
                    .group(work)
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(LOGGINGHANDLER);
                                    pipeline.addLast(LTPCODEC);
                                    pipeline.addLast(new UserHandler());
                                }
                            }
                    ).connect(URI, PORT)
                    .sync()
                    .channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            work.shutdownGracefully();
        }
    }
}
