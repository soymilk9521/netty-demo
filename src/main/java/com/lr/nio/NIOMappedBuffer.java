package com.lr.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/19 9:20
 */
public class NIOMappedBuffer {
    public static void main(String[] args) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("file01.txt", "rw")) {
            final FileChannel channel = randomAccessFile.getChannel();
            /**
             * 参数1：文件读写模式
             * 参数2 :可以直接修改的起始位置
             * 参数3： 映射到内存的大小（不是索引位置），即有多少个自己映射到内存，可以直接修改的范围是0-5
             * 时间类型是DirectByteBuffer
             */
            final MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            mappedBuffer.put(0, (byte)'H');
            mappedBuffer.put(3, (byte)'9');
            mappedBuffer.put(4, (byte)'Y');
            // mappedBuffer.put(5, (byte)'T'); // java.lang.IndexOutOfBoundsException
            System.out.println("修改成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
