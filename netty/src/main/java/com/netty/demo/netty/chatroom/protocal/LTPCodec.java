package com.netty.demo.netty.chatroom.protocal;

import com.netty.demo.netty.chatroom.Enum.SerializationAlgorithmEnum;
import com.netty.demo.netty.chatroom.message.Message;
import com.netty.demo.netty.chatroom.serialize.SerializationAlgorithmFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.netty.demo.netty.chatroom.Enum.SerializationAlgorithmEnum.JDK;

// MessageToMessageCodec 出站、入站 handler
// MessageToMessageCodec<ByteBuf,Message> 入站类型 ByteBuf， 出站类型 Message
@Slf4j
@ChannelHandler.Sharable
public class LTPCodec extends MessageToMessageCodec<ByteBuf,Message> {

    private static final byte[] MAGIC_NUMBER= {'L','I','J','I','E'};
    private static final byte VERSION = 1;
    private static final SerializationAlgorithmEnum SERIALIZATION_ALGORITHM = JDK;
    // 出站的时候调用 将message按照LTP协议编码为ByteBuf
    // 将处理完成的对象放到 List<Object> list中则可以被后续handler处理
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf buffer = channelHandlerContext.alloc().buffer()
                .writeBytes(MAGIC_NUMBER) // 5
                .writeByte(VERSION) // 1
                .writeByte(SERIALIZATION_ALGORITHM.getAlgorithmEnumType()) // 1
                .writeByte(message.getMessageType()) // 1
                .writeInt(message.getSequenceId()); // 4
        // 序列化
        byte[] serializedMessage = SerializationAlgorithmFactory
                .getSerializationAlgorithm(SERIALIZATION_ALGORITHM.getAlgorithmEnumType())
                .serialize(message);
        buffer.writeInt(serializedMessage.length) // 4
                .writeBytes(serializedMessage);
        list.add(buffer);
    }

    // 入站的时候调用 将ByteBuf按照LTP协议解码为message
    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] magicNum = new byte[MAGIC_NUMBER.length];
        byteBuf.readBytes(magicNum,0,MAGIC_NUMBER.length);
        byte version = byteBuf.readByte();
        byte serializationAlgorithmType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        int contentLen = byteBuf.readInt();

        // 这种情况是不对的，如果值超过127，defaultCharset(utf-8) 解码的时候会出现问题
        // 应该先创建byte数组，再将值直接拷贝到byte数组中
        // ByteBuf content = byteBuf.readBytes(contentLen);
        // byte[] bytes = content.toString(Charset.defaultCharset()).getBytes();
        byte[] contents = new byte[contentLen];
        byteBuf.readBytes(contents,0,contentLen);
        // content 反序列化
        Message deserializedContent = (Message)SerializationAlgorithmFactory
                .getSerializationAlgorithm(SERIALIZATION_ALGORITHM.getAlgorithmEnumType())
                .deserialize(contents);
        log.info("magicNum:{},version:{},serializationAlgorithmType:{},messageType:{},sequenceId:{},contentLen:{},",
                magicNum,version,serializationAlgorithmType,messageType,sequenceId,contentLen);
        log.info("content:{}",deserializedContent);
        list.add(deserializedContent);
    }
}
