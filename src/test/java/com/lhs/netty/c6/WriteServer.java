package com.lhs.netty.c6;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * WriteServer
 *
 * 服务端向客户端发送数据
 *
 * @author longhuashen
 * @since 2021-04-11
 */
@Slf4j
public class WriteServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);

                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i =  0; i < 30000000; i++) {
                        stringBuffer.append("a");
                    }

                    ByteBuffer byteBuffer = Charset.defaultCharset().encode(stringBuffer.toString());
                    while (byteBuffer.hasRemaining()) {
                        int write = sc.write(byteBuffer);
                        log.info("write:{}", write);
                    }
                }
            }
        }
    }
}
