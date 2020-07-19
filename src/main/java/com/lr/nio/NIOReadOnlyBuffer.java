package com.lr.nio;

import java.nio.ByteBuffer;

/**
 * <p>
 *  只读buffer，put时java.nio.ReadOnlyBufferException异常
 * </p>
 *
 * @author LR
 * @since 2020/07/19 8:51
 */
public class NIOReadOnlyBuffer {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 63; i++) {
            byteBuffer.put((byte)i);
        }
        byteBuffer.put((byte) 63);
        // byteBuffer.put((byte) 64); // java.nio.BufferOverflowException

        byteBuffer.flip();

        final ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }


        System.out.println("===============================");

        readOnlyBuffer.put((byte) 100); // java.nio.ReadOnlyBufferException
    }
}
