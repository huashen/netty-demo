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
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * MultiThreadServer
 *
 * @author longhuashen
 * @since 2021-04-15
 */
@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        Selector boss = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2.建立selector和channel的联系(注册)
        SelectionKey sscKey = ssc.register(boss, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8081));

        Worker worker = new Worker("workder-0");

        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    log.debug("connected:{}", sc.getRemoteAddress());
                    sc.configureBlocking(false);
                    log.debug("before register:{}", sc.getRemoteAddress());
                    worker.register(sc);
                    log.debug("after register:{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {

        private Thread thread;

        private Selector selector;


        private String name;

        private volatile boolean start = false;//还未初始化

        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                this.thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                start = true;
            }

            queue.add(()-> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            int read = channel.read(buffer);
                            if (read > -1) {
                                log.debug("read:{}",  channel.getRemoteAddress());
                            }
                            buffer.flip();
                        }
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}
