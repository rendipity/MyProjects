package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.message.PingMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class PingMessageHandler extends SimpleChannelInboundHandler<PingMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        log.info("接收到来自 {} 的心跳包",ctx.channel());
    }
}
