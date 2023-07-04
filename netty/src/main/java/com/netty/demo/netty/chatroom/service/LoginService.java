package com.netty.demo.netty.chatroom.service;

public class LoginService {

    public Boolean login(String username, String password){
        return "admin".equals(password);
    }
}
