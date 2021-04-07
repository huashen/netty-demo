package com.lhs.netty.c6;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
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
                log.debug("key:{}", key);

                //取消事件
                key.cancel();
//                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//                SocketChannel sc = channel.accept();
//                log.debug("sc:{}", sc);
            }

        }
    }
}
