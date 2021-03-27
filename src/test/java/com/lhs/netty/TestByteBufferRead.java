package com.lhs.netty;

import java.nio.ByteBuffer;

/**
 * TestByteBufferRead
 *
 * @author longhuashen
 * @since 2021-03-28
 */
public class TestByteBufferRead {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[]{'a', 'b', 'c', 'd'});
        byteBuffer.flip();
        byteBuffer.get(new byte[4]);
    }
}
