package com.netty.demo.chatroom;

import com.netty.demo.netty.chatroom.message.LoginRequestMessage;
import com.netty.demo.netty.chatroom.protocal.LTPCodec;
import com.netty.demo.netty.chatroom.serialize.JDKSerializationAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

public class test {
    
    @Test
    public void LTPCodecTest(){
        LTPCodec ltpCodec = new LTPCodec();
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(loggingHandler, ltpCodec);
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("lijie","123456");
        embeddedChannel.writeOutbound(loginRequestMessage);
        Object out = embeddedChannel.readOutbound();
        embeddedChannel.writeInbound(out);
    }
   @Test
    public void serializationTest(){
        JDKSerializationAlgorithm jdkSerializationAlgorithm = new JDKSerializationAlgorithm();
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("lijie","123456");
        byte[] bytes = jdkSerializationAlgorithm.serialize(loginRequestMessage);
        System.out.println(Arrays.toString(bytes));
        System.out.println(jdkSerializationAlgorithm.deserialize(bytes));

    }

    /**
     * 研究
     * readBytes(contents,0,contentLen)
     * 和 readBytes(contentLen);
     *    byte[] bytes = content.toString(Charset.defaultCharset()).getBytes())
     * 的区别
     */
// [-17, -65, -67, -17, -65, -67, 0, 5, 115, 114, 0, 50, 99, 111, 109, 46, 110, 101, 116, 116, 121, 46, 100, 101, 109, 111, 46, 110, 101, 116, 116, 121, 46, 99, 104, 97, 116, 114, 111, 111, 109, 46, 109, 101, 115, 115, 97, 103, 101, 46, 76, 111, 103, 105, 110, 77, 101, 115, 115, 97, 103, 101, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, 2, 0, 2, 76, 0, 8, 112, 97, 115, 115, 119, 111, 114, 100, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 0, 8, 117, 115, 101, 114, 110, 97, 109, 101, 113, 0, 126, 0, 1, 120, 112, 116, 0, 6, 49, 50, 51, 52, 53, 54, 116, 0, 5, 108, 105, 106, 105, 101]
// [-84, -19, 0, 5, 115, 114, 0, 50, 99, 111, 109, 46, 110, 101, 116, 116, 121, 46, 100, 101, 109, 111, 46, 110, 101, 116, 116, 121, 46, 99, 104, 97, 116, 114, 111, 111, 109, 46, 109, 101, 115, 115, 97, 103, 101, 46, 76, 111, 103, 105, 110, 77, 101, 115, 115, 97, 103, 101, -1, -1, -1, -1, -1, -1, -1, -1, 2, 0, 2, 76, 0, 8, 112, 97, 115, 115, 119, 111, 114, 100, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 0, 8, 117, 115, 101, 114, 110, 97, 109, 101, 113, 0, 126, 0, 1, 120, 112, 116, 0, 6, 49, 50, 51, 52, 53, 54, 116, 0, 5, 108, 105, 106, 105, 101]
//            0, 5, 115, 114, 0, 50, 99, 111, 109, 46, 110, 101, 116, 116, 121, 46, 100, 101, 109, 111, 46, 110, 101, 116, 116, 121, 46, 99, 104, 97, 116, 114, 111, 111, 109, 46, 109, 101, 115, 115, 97, 103, 101, 46, 76, 111, 103, 105, 110, 77, 101, 115, 115, 97, 103, 101, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, -17, -65, -67, 2, 0, 2, 76, 0, 8, 112, 97, 115, 115, 119, 111, 114, 100, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 0, 8, 117, 115, 101, 114, 110, 97, 109, 101, 113, 0, 126, 0, 1, 120, 112, 116, 0, 6, 49, 50, 51, 52, 53, 54, 116, 0, 5, 108, 105, 106, 105, 101]
    @Test
    public void byteBufTest(){

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeByte(128);
        System.out.println(byteBuf.readerIndex());
        for (int i = 0; i < 4; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println(byteBuf.readerIndex());
        byte[] bytes = byteBuf.toString(Charset.defaultCharset()).getBytes();
        System.out.println(Arrays.toString(bytes));

        /*final byte[] MAGIC_NUMBER= {'L','I','J','I','E'};
        final byte VERSION = 1;
        final SerializationAlgorithmEnum SERIALIZATION_ALGORITHM = JDK;
        LoginMessage message = new LoginMessage("lijie","123456");
        //编码
        ByteBuf buffer =  ByteBufAllocator.DEFAULT.buffer()
                .writeBytes(MAGIC_NUMBER) // 5
                .writeByte(VERSION) // 1
                .writeByte(SERIALIZATION_ALGORITHM.getAlgorithmEnumType()) // 1
                .writeByte(message.getMessageType()) // 1
                .writeInt(message.getSequenceId()); // 4
        // 序列化
        byte[] serializedMessage = SerializationAlgorithmFactory
                .getSerializationAlgorithm(SERIALIZATION_ALGORITHM.getAlgorithmEnumType())
                .serialize(message);
        System.out.println(Arrays.toString(buffer.toString(StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8)));
        System.out.println(serializedMessage.length);
        buffer.writeInt(serializedMessage.length); // 4
        //buffer.writeBytes(serializedMessage);

        System.out.println(Arrays.toString(buffer.toString(Charset.defaultCharset()).getBytes()));*/

        /*//  解码
        System.out.println(magicNum);
        byte version = byteBuf.readByte();
        byte serializationAlgorithmType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        int contentLen = byteBuf.readInt();
        byte[] contents = new byte[contentLen];
        // ByteBuf content = byteBuf.readBytes(contentLen);
        // byte[] bytes = content.toString(Charset.defaultCharset()).getBytes();
        byteBuf.readBytes(contents,0,contentLen);
        System.out.println(Arrays.toString(contents));
        ByteBuf byteBuf1 = byteBuf.readBytes(5);
        System.out.println(Arrays.toString(byteBuf1.toString(StandardCharsets.UTF_8).getBytes())); */
    }


}
