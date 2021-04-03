package com.lhs.netty.c5;

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
 * 非阻塞模式
 *
 * @author longhuashen
 * @since 2021-04-01
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        ssc.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept();//非阻塞，线程继续运行 如果没有连接建立，sc是null

            if (sc != null) {
                log.debug("connected...{}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }


            for (SocketChannel channel : channels) {
                int read = channel.read(buffer);//非阻塞方法，线程继续运行

                if (read > 0) {
                    buffer.flip();

                    buffer.clear();

                    log.debug("after read...{}", channel);
                }
            }
        }
    }
}
