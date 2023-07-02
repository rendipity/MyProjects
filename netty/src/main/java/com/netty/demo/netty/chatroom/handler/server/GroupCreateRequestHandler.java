package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.Session.GroupSessionFactory;
import com.netty.demo.netty.chatroom.Session.SessionFactory;
import com.netty.demo.netty.chatroom.message.GroupCreateRequestMessage;
import com.netty.demo.netty.chatroom.message.GroupCreateResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupCreateRequestHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = GroupSessionFactory.getMembers(groupName);
        if (members != null){
            ctx.writeAndFlush(new GroupCreateResponseMessage(false,"群聊【"+groupName+"】已经存在"));
            return;
        }
        members = msg.getMembers();
        // todo 判断群成员是否存在
        GroupSessionFactory.create(groupName,members);
        GroupCreateResponseMessage responseMessage = new GroupCreateResponseMessage(msg.getFrom(), msg.getGroupName(), "你已经成功加入群聊【"+groupName+"】");
        // 通知所有群成员被拉入新群
        for(String memberName: members){
            Channel memberChannel = SessionFactory.getByUsername(memberName);
            memberChannel.writeAndFlush(responseMessage);
        }
    }
}
