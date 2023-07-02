package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.Session.GroupSessionFactory;
import com.netty.demo.netty.chatroom.Session.SessionFactory;
import com.netty.demo.netty.chatroom.message.GroupChatRequestMessage;
import com.netty.demo.netty.chatroom.message.GroupChatResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

import static com.netty.demo.netty.chatroom.Enum.ErrorResultEnum.GROUP_NOT_EXIST;

@ChannelHandler.Sharable
public class GroupChatHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = GroupSessionFactory.getMembers(groupName);
        if (members == null){
            ctx.writeAndFlush(GroupChatResponseMessage.fail(GROUP_NOT_EXIST));
            return ;
        }
        GroupChatResponseMessage groupChatResponseMessage = new GroupChatResponseMessage(msg.getFrom(), msg.getContent());
        for (String member:members) {
            Channel memberChannel = SessionFactory.getByUsername(member);
            memberChannel.writeAndFlush(groupChatResponseMessage);
        }
    }
}
