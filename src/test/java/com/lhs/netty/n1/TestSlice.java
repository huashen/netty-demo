package com.lhs.netty.n1;

import com.lhs.netty.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * TestSlice
 *
 * @author longhuashen
 * @since 2021-04-28
 */
public class TestSlice {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        LogUtil.log(buf);

        //数据没有发生复制 效率高
        ByteBuf buf1 = buf.slice(0, 5);
        ByteBuf buf2 = buf.slice(5, 5);
        LogUtil.log(buf1);
        LogUtil.log(buf2);

        System.out.println("-----");
        buf1.setByte(0, 'b');
        LogUtil.log(buf1);
        LogUtil.log(buf);

    }
}
