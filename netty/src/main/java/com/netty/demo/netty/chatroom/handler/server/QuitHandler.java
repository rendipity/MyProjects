package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.Session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    /**
     * 正常退出是客户端发送的事件
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.unbind(ctx.channel());
        log.info("{} 已经断开",ctx.channel());
    }

    /**
     * 程序中断时是客户端发送的事件
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SessionFactory.unbind(ctx.channel());
        log.info("{} 已经断开",ctx.channel());
    }
}
