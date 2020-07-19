package com.lr.nio;

import java.nio.ByteBuffer;

/**
 * <p>
 *  写入的顺序和读取的顺序保持一致，否则java.nio.BufferUnderflowException异常。
 * </p>
 *
 * @author LR
 * @since 2020/07/19 8:44
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(100);
        byteBuffer.putLong(9l);
        byteBuffer.putChar('尚');
        byteBuffer.putShort((short)4);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        // System.out.println(byteBuffer.getLong()); // java.nio.BufferUnderflowException
    }
}
