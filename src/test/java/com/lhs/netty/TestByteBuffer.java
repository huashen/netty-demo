package com.lhs.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TestByteBuffer
 *
 * @author longhuashen
 * @since 2021-03-27
 */
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        try (FileChannel fileChannel = new FileInputStream("data.txt").getChannel()){
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                int len = fileChannel.read(buffer);
                log.debug("读取到的字节数：{}", len);
                if (len == -1) {
                    break;
                }

                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("实际字节：{}", (char)b);
                }
                buffer.clear();
            }
        } catch (IOException e) {

        }
    }
}
