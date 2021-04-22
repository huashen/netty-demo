package com.lhs.netty.n1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * EventLoopClient
 *
 * @author longhuashen
 * @since 2021-04-23
 */
@Slf4j
public class EventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                //异步非阻塞 main发起了调用，真正执行 connect是nio线程
                .connect(new InetSocketAddress("localhost", 8081));

        //使用sync方法同步处理结果
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello,world");

        //使用addListener(回调对象)方法异步处理结果
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                Channel channel = channelFuture.channel();
//                log.debug("{}", channel);
//                channel.writeAndFlush("hello, world");
//            }
//        });
    }
}
