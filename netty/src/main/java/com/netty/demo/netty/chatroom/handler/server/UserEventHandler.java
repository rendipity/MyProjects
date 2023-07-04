package com.netty.demo.netty.chatroom.handler.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
// 出站入站处理器
public class UserEventHandler extends ChannelDuplexHandler {

    // 捕捉用户事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        if (event.state() == IdleState.READER_IDLE) {
            log.info("{} 5s 没有发送消息了",ctx.channel());
            ctx.channel().close();
        }
    }
}
