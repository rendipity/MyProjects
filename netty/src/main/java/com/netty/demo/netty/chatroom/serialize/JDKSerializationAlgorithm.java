package com.netty.demo.netty.chatroom.serialize;

import java.io.*;

public class JDKSerializationAlgorithm implements SerializationAlgorithm{

    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Object deserialize(byte[] object) {
        Object result =null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(object);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            result = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
