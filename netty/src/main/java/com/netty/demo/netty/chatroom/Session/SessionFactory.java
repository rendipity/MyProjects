package com.netty.demo.netty.chatroom.Session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class SessionFactory {

    static HashMap<String, Channel> sessions = new HashMap<>();

    public static void register(String username, Channel socketChannel){
        sessions.put(username,socketChannel);
    }

    public static Channel getByUsername(String username){
        return sessions.get(username);
    }

    public static void unbind(Channel socketChannel){
        for(Map.Entry<String, Channel> entry:sessions.entrySet()){
            if (entry.getValue().equals(socketChannel)){
                sessions.remove(entry.getKey());
                break;
            }
        }
    }
}
