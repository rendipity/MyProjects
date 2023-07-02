package com.netty.demo.netty.chatroom.handler.client;

import com.netty.demo.netty.chatroom.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class UserHandler extends ChannelInboundHandlerAdapter {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    AtomicBoolean LOGIN = new AtomicBoolean(false);
    @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("msg:{}",msg);
            if (msg instanceof LoginResponseMessage){
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage)msg;
                if (loginResponseMessage.isSuccess()){
                    LOGIN.set(true);
                }
                countDownLatch.countDown();
            }
        }
        // 建立连接事件
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 单独开启一个线程来处理用户IO，避免用户IO阻塞网络IO
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new UserInputRunnable(ctx));
        }

        class UserInputRunnable implements Runnable{
            private final ChannelHandlerContext ctx;
            private final Scanner s = new Scanner(System.in);
            public UserInputRunnable(ChannelHandlerContext ctx) {
                this.ctx = ctx;
            }
            @Override
            public void run() {
                LoginRequestMessage loginRequestMessage = getUserInfo();
                ctx.writeAndFlush(loginRequestMessage);
                System.out.println("登陆中，请稍等....");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!LOGIN.get()){
                    System.out.println("账号或密码错误");
                    ctx.channel().close();
                }
                printHelpMenu();
                while (true){
                    String command = s.nextLine();
                    boolean validate = commandValidate(command);
                    if (!validate){
                        System.out.println("操作有误，请重试");
                        continue;
                    }
                    String[] part = command.split(" ",3);
                    switch (part[0]){
                        case "send":
                            ctx.writeAndFlush(new ChatRequestMessage(loginRequestMessage.getUsername(),part[1],part[2]));
                            break;
                        case "gcreate":
                            String[] members = part[2].split(",");
                            Set<String> membersSet = Arrays.stream(members).collect(Collectors.toSet());
                            membersSet.add(loginRequestMessage.getUsername());
                            ctx.writeAndFlush(new GroupCreateRequestMessage(loginRequestMessage.getUsername(),part[1],membersSet));
                            break;
                        case "gsend":
                            ctx.writeAndFlush(new GroupChatRequestMessage(loginRequestMessage.getUsername(),part[1],part[2]));
                            break;
                        case "gjoin":
                            System.out.println("暂不支持----升级中");
                            break;
                        case "gquit":
                            System.out.println("暂不支持----升级中");
                            break;
                        case "quit":

                            break;
                        case "help":
                        printHelpMenu();
                        break;
                    }
                }
            }
            private  LoginRequestMessage getUserInfo(){
                System.out.println("请输入用户名");
                String username = s.next();
                System.out.println("请输入密码");
                String password = s.next();
                LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                return loginRequestMessage;
            }
            private void printHelpMenu(){
                System.out.println("==================");
                System.out.println("发送个人消息: send [username] [content]");
                System.out.println("发送群聊消息: gsend [group_name] [content]");
                System.out.println("创建群聊: gcreate [group_name] [m1,m2,m3...]");
                System.out.println("加入群聊: gjoin [group_name]");
                System.out.println("退出群聊: gquit [group_name]");
                System.out.println("帮助菜单: help");
                System.out.println("退出登陆: quit");
            }
            private boolean commandValidate(String command){
                // todo 用户输入的命令校验
                return true;
            }
        }
}
