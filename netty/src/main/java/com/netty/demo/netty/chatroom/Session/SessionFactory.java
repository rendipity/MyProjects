package com.netty.demo.netty.chatroom.Session;

import io.netty.channel.Channel;

import java.util.HashMap;

public class SessionFactory {

    static HashMap<String, Channel> sessions = new HashMap<>();

    public static void register(String username, Channel socketChannel){
        sessions.put(username,socketChannel);
    }

    public static Channel getByUsername(String username){
        return sessions.get(username);
    }
}
