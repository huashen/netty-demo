package com.lhs.netty;

import java.nio.ByteBuffer;

/**
 * TestByteBufferAllocate
 *
 * @author longhuashen
 * @since 2021-03-28
 */
public class TestByteBufferAllocate {

    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

        /**
         * class java.nio.HeapByteBuffer -java堆内存，读写效率低，受到gc影响
         * class java.nio.DirectByteBuffer -直接内存，读写效率高(少一次拷贝),不受gc影响
         */
    }
}
