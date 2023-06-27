package com.netty.demo.netty.HelloWorld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
    public static void main(String[] args) {
        // 1. 启动器 负责组装 netty组件 启动服务器
        new ServerBootstrap()
                // 2. EventGroup (Selector,Thread) 的group组
                .group(new NioEventLoopGroup())
                    // 3. 使用哪一个serverSocketChannel的具体实现类
                .channel(NioServerSocketChannel.class)
                    // 4. child 能够执行哪些操作 handler
                .childHandler(
                        // 5. 初始化和客户端的连接通道channel  负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            // 6. 添加具体的handler
                        nioSocketChannel.pipeline().addLast(new StringDecoder()); // 将byteBuf 转化为String
                        // 自定义handler
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                    // 7. 绑定端口
                }).bind(8080);
    }
}
