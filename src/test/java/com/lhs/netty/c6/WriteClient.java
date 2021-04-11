package com.lhs.netty.c6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * WriteClient
 *
 * @author longhuashen
 * @since 2021-04-11
 */
public class WriteClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

        int count = 0;
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(byteBuffer);
            System.out.println(count);
            byteBuffer.clear();
        }
    }
}
