package com.netty.demo.netty.chatroom.handler.server;

import com.netty.demo.netty.chatroom.Session.SessionFactory;
import com.netty.demo.netty.chatroom.message.LoginRequestMessage;
import com.netty.demo.netty.chatroom.message.LoginResponseMessage;
import com.netty.demo.netty.chatroom.service.LoginService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    LoginService loginService = new LoginService();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        LoginResponseMessage response = null; ;
        if (loginService.login(msg.getUsername(),msg.getPassword())) {
            log.info("{} 登陆成功",msg.getUsername());
            Channel channel = ctx.channel();
            SessionFactory.register(msg.getUsername(), channel);
            response = LoginResponseMessage.success();
        }else
            response = LoginResponseMessage.fail();
        ctx.writeAndFlush(response);
    }
}
