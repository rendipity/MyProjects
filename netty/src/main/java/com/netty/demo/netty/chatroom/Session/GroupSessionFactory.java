package com.netty.demo.netty.chatroom.Session;

import java.util.HashMap;
import java.util.Set;

public class GroupSessionFactory {

    static HashMap<String, Set<String>> groups = new HashMap<>();

    public static void create(String groupName, Set<String> members) {
        groups.put(groupName,members);
    }
    public static Set<String> getMembers(String groupName) {
        return  groups.get(groupName);
    }
}