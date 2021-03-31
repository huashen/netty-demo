package com.lhs.netty;

import java.nio.ByteBuffer;

/**
 * TestByteBufferExam
 *
 * 黏包半包解析
 *
 * @author longhuashen
 * @since 2021-03-31
 */
public class TestByteBufferExam {

    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            //找到一条完整信息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                //从source读，向target写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
            }
        }
        source.compact();
    }
}
