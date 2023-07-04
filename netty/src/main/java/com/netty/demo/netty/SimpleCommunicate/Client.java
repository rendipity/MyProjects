package com.netty.demo.netty.SimpleCommunicate;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

@Slf4j
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup messageEventLoopGroup = null;
        messageEventLoopGroup = new NioEventLoopGroup();
        ChannelFuture future = new Bootstrap()
                .group(messageEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new ClientHandler());
                    }
                })
                .connect(new InetSocketAddress("localhost",8888));
        future.addListener( future1 -> {
            if (future1.isSuccess()){
                log.debug("连接成功！");
            }else{
                log.debug("连接失败！");
            }
        });

    }
    static  class ClientHandler extends ChannelInboundHandlerAdapter{
        // 建立连接成功
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 客户端率先发送数据
            ByteBuf byteBuf = ctx.alloc().buffer().writeBytes("hello".getBytes());
            log.info("send message: {}",byteBuf.toString(Charset.defaultCharset()));
            ctx.writeAndFlush(byteBuf);
        }

        // 读事件
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf receive = (ByteBuf) msg;
            log.info("receive message: {}",receive.toString(Charset.defaultCharset()));
        }
    }
}
