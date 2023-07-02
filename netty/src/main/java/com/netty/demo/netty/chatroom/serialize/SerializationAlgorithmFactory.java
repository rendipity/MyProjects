package com.netty.demo.netty.chatroom.serialize;

import com.netty.demo.netty.chatroom.Enum.SerializationAlgorithmEnum;

import java.util.HashMap;

import static com.netty.demo.netty.chatroom.Enum.SerializationAlgorithmEnum.JDK;

public class SerializationAlgorithmFactory {

    static HashMap<SerializationAlgorithmEnum, SerializationAlgorithm> hashMap = new HashMap<>();
    static {
        hashMap.put(JDK,new JDKSerializationAlgorithm());
    }

    public static SerializationAlgorithm getSerializationAlgorithm(Integer algorithmType){
        SerializationAlgorithmEnum algorithmEnum = SerializationAlgorithmEnum.getByType(algorithmType);
        return hashMap.get(algorithmEnum);
    }
}
