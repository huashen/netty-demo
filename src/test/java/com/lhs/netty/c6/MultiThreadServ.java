package com.lhs.netty.c6;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * MultiThreadServ
 *
 * @author longhuashen
 * @since 2021-04-15
 */
public class MultiThreadServ {

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
                this.name = name;
                this.start = start;
                worker = Selector.open();
                start = true;
            }
        }

        @Override
        public void run() {

        }
    }
}
