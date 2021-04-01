package com.lhs.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Server
 *
 * @author longhuashen
 * @since 2021-04-01
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);

        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.debug("connecting...");
            SocketChannel sc = ssc.accept();//阻塞方法，线程停止运行
            log.debug("connected...{}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                log.debug("before read...{}", channel);
                channel.read(buffer);//阻塞方法，线程停止运行

                buffer.flip();

                buffer.clear();

                log.debug("after read...{}", channel);
            }
        }
    }
}
