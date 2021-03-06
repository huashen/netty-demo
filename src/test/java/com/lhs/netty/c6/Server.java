package com.lhs.netty.c6;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Server
 * <p>
 * 非阻塞模式
 *
 * @author longhuashen
 * @since 2021-04-01
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        //1.创建selector 管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2.建立selector和channel的联系(注册)
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            //3.select方法 没有事件发生，线程阻塞 有事件，线程才会恢复运行
            selector.select();

            //4.处理时间，selectkeys 内部包含了所有发生的时间
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //移除key
                iterator.remove();

                log.debug("key:{}", key);

                if (key.isAcceptable()) {

                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}", sc);

                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);//如果正常断开，read的方法的返回值是-1
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                              ByteBuffer newByteBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                              buffer.flip();
                              newByteBuffer.put(buffer);
                              key.attach(newByteBuffer);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            //找到一条完整信息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                //从source读，向target写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
            }
        }
        source.compact();
    }
}
