package com.lhs.netty;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * TestByteBufferString
 *
 * @author longhuashen
 * @since 2021-03-29
 */
public class TestByteBufferString {

    public static void main(String[] args) {
        //第一种
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("hello".getBytes());

        //第二种 自动切换到读模式
        ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode("hello");

        //第三种 自动切换到读模式
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());

        String s = StandardCharsets.UTF_8.decode(byteBuffer1).toString();
        System.out.println(s);
    }
}
