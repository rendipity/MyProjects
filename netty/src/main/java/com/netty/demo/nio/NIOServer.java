package com.netty.demo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * nio服务器
 */
// todo 多个发送者
// todo 处理消息边界问题
@Slf4j
public class NIOServer {
    private final Selector connectSelector;
    private final Worker worker;
    public NIOServer(Integer port) throws IOException {
        // 使用ServerSocketChannel建立连接
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        this.connectSelector = Selector.open();
        ssc.register(connectSelector, SelectionKey.OP_ACCEPT,null);
        this.worker = new Worker("work-1");
    }
    public void run() throws IOException {
        // 建立连接后将SocketChannel交给Worker线程负责事件处理
        while(true){
            connectSelector.select();
            Iterator<SelectionKey> iterator = connectSelector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
                // select的事件集合为了保障我们可能需要重复的情况，所以不会手动清除这个已经已经被响应的事件，需要我们主动去清除
                iterator.remove();
                if (next.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)next.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    log.debug("connect before: {}",sc);
                    this.worker.register(sc);
                    log.debug("connect after: {}",sc);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            NIOServer server =new NIOServer(8080);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
@Slf4j
class Worker implements Runnable{
    private String name;
    private WorkerType type;
    private Selector selector;
    private Thread thread;

    private boolean init = false;
    private ConcurrentLinkedDeque<Runnable> runnableQueue;

    public Worker(String name) {
        this.name = name;
        this.type = WorkerType.READER;
    }

    public void register(SocketChannel sc) throws IOException {
        if (!init){
            this.selector = Selector.open();
            this.thread = new Thread(this,this.name);
            this.thread.start();
            this.runnableQueue = new ConcurrentLinkedDeque<>();
            init = true;
        }
        // 使用消息队列解耦线程间通信
        runnableQueue.add(()->{
            try {
                sc.register(this.selector,SelectionKey.OP_READ,null);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
        // 唤醒阻塞中的selector
        this.selector.wakeup();
    }

    @Override
    public void run() {
        while(true){
            try {
                this.selector.select();
                while(!runnableQueue.isEmpty()){
                    Runnable task = runnableQueue.poll();
                    task.run();
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    // select 的事件集合为了保障我们可能需要重复的情况，所以不会手动清除这个已经已经被响应的事件，需要我们主动去清除
                    iterator.remove();
                    if (next.isReadable()){
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        // 如果read的返回值是-1 则说明这个读事件是socket断开产生的读事件，将socket给cancel掉
                        if (read==-1){
                            next.cancel();
                            SocketAddress remoteAddress = channel.getRemoteAddress();
                            log.debug("client {} 断开连接",remoteAddress);
                            continue;
                        }
                        buffer.flip();
                        char[] msg = new char[16];
                        int index=0;
                        while(buffer.hasRemaining()){
                            msg[index++]=(char) buffer.get();
                        }
                        msg = Arrays.copyOf(msg, index);
                        log.debug("message:{}", Arrays.toString(msg));
                        buffer.clear();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
enum WorkerType{
    READER,WRITER;
}
