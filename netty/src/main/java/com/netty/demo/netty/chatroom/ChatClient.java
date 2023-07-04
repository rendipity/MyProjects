package com.netty.demo.netty.chatroom;

import com.netty.demo.netty.chatroom.handler.client.ChannelCloseHandler;
import com.netty.demo.netty.chatroom.handler.client.UserWriteIdelEventHandler;
import com.netty.demo.netty.chatroom.handler.client.UserHandler;
import com.netty.demo.netty.chatroom.handler.common.MyFrameDecoder;
import com.netty.demo.netty.chatroom.protocal.LTPCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        String URI = "localhost";
        int PORT = 8080;
        NioEventLoopGroup work = new NioEventLoopGroup();
        LTPCodec LTPCODEC = new LTPCodec();
        LoggingHandler LOGGINGHANDLER = new LoggingHandler();
        UserWriteIdelEventHandler USERWRITEIDELEVENTHANDLER = new UserWriteIdelEventHandler();
        ChannelCloseHandler CHANNEL_CLOSE_HANDLER = new ChannelCloseHandler();
        try {
            Channel channel = new Bootstrap()
                    .group(work)
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(new IdleStateHandler(0,2,0));
                                    //pipeline.addLast(LOGGINGHANDLER);
                                    pipeline.addLast(new MyFrameDecoder());
                                    pipeline.addLast(LTPCODEC);
                                    pipeline.addLast(new UserHandler());
                                    pipeline.addLast(USERWRITEIDELEVENTHANDLER);
                                    pipeline.addLast(CHANNEL_CLOSE_HANDLER);
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
