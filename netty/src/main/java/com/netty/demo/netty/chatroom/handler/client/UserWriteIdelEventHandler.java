package com.netty.demo.netty.chatroom.handler.client;

import com.netty.demo.netty.chatroom.message.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
// 出站入站处理器
public class UserWriteIdelEventHandler extends ChannelDuplexHandler {

    // 捕捉用户事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        if (event.state() == IdleState.WRITER_IDLE) {
            log.info("2s了，发送心跳包");
            ctx.writeAndFlush(new PingMessage());
        }
    }
}
