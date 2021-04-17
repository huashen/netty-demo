package com.lhs.netty.c6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * MultiThreadClient
 *
 * @author longhuashen
 * @since 2021-04-17
 */
public class MultiThreadClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8081));
        System.out.println("waiting...");
    }
}
