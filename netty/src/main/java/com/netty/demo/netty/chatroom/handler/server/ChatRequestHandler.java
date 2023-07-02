package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.Session.SessionFactory;
import com.netty.demo.netty.chatroom.message.ChatRequestMessage;
import com.netty.demo.netty.chatroom.message.ChatResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatRequestHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        Channel receiverChannel = SessionFactory.getByUsername(msg.getTo());
        // 不在线则提示对方不在线
        if (receiverChannel == null){
            ctx.writeAndFlush(ChatResponseMessage.fail("对方离线中"));
            return;
        }
        // 在线则成功发送消息
        ChatResponseMessage chatResponseMessage = new ChatResponseMessage(msg.getFrom(), msg.getTo(), msg.getContent());
        receiverChannel.writeAndFlush(chatResponseMessage);
    }
}
