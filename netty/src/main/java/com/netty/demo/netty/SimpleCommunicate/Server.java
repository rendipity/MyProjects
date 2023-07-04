package com.netty.demo.netty.SimpleCommunicate;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup connectEventLoop = null;
        NioEventLoopGroup msgEventLoop =null;
        connectEventLoop = new NioEventLoopGroup();
        msgEventLoop = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(connectEventLoop,msgEventLoop)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                    }
                })
                .bind(8888);
    }
    static class ServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 接收消息
            ByteBuf message = (ByteBuf) msg;
            String mes = message.toString(Charset.defaultCharset());
            log.debug("receive message {}",mes);
            message.release();

            // 回复消息
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes(("i have receive your message:"+mes).getBytes());
            log.debug("send message: {}",buffer.toString(Charset.defaultCharset()));
            ctx.writeAndFlush(buffer);
        }
    }
}
