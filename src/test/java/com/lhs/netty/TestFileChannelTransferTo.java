package com.lhs.netty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * TestFileChannelTransferTo
 *
 * @author longhuashen
 * @since 2021-03-31
 */
public class TestFileChannelTransferTo {

    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
            FileChannel to = new FileOutputStream("to.txt").getChannel();
        ) {
            //java内部使用零拷贝进行传输 单次上限是2g
//            from.transferTo(0, from.size(), to);

            //优化单次上限
            long size = from.size();
            for (long left = size; left > 0;) {
                left -= from.transferTo((size - left), left, to);
            }
        } catch (IOException e) {
        }
    }
}
