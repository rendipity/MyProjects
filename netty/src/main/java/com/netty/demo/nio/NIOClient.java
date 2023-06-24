package com.netty.demo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

@Slf4j
public class NIOClient {
    private SocketChannel sc;
    public NIOClient(String address, Integer port) throws IOException {
        this.sc= SocketChannel.open();
        sc.connect(new InetSocketAddress(address,port));
    }
    public void send(String message) throws IOException {
        sc.write(Charset.defaultCharset().encode(message));
    }
    public static void main(String[] args) {
        try {
            NIOClient client =new NIOClient("localhost",8080);
            Scanner scanner =new Scanner(System.in);
            while(true){
                String msg = scanner.next();
                client.send(msg);
                log.debug("send message: {}",msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
