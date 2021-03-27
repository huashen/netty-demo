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
        try (FileChannel fileChannel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                //从channel读取数据，向buffer写入
                int len = fileChannel.read(buffer);
                log.debug("读取到的字节数：{}", len);
                if (len == -1) {
                    break;
                }

                //切换至读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("实际字节：{}", (char) b);
                }
                //切换写模式
                buffer.clear();
            }
        } catch (IOException e) {

        }
    }
}
