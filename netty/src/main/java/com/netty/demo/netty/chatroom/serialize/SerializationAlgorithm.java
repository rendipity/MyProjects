package com.netty.demo.netty.chatroom.serialize;

public interface SerializationAlgorithm {

    byte[] serialize(Object object);

    Object deserialize(byte[] object);
}
