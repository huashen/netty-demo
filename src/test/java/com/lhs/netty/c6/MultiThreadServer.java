package com.lhs.netty.c6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * MultiThreadServer
 *
 * @author longhuashen
 * @since 2021-04-15
 */
public class MultiThreadServer {

    public static void main(String[] args) {

    }

    class Worker implements Runnable {

        private Thread thread;

        private Selector worker;

        private String name;

        private volatile boolean start = false;//还未初始化

        public void register() throws IOException {
            if (!start) {
                this.thread = new Thread(this, name);
                thread.start();
                worker = Selector.open();
                start = true;
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    worker.select();
                    Iterator<SelectionKey> iterator = worker.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            channel.read(buffer);
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
