package com.lr.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/18 10:06
 */
public class NIOFileChannel01 {
    public static void main(String[] args) {
        String str = "hello， 尚硅谷";
        // 创建一个输出流 -> channel
        try (FileOutputStream fileOutputStream = new FileOutputStream("file01.txt")) {
            // 通过fileOutputStream获取FileChannel
            // 这个FileChannel的实现类是FileChannelImpl
            FileChannel channel = fileOutputStream.getChannel();
            // 创建ByteBuffer缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 将str写入缓冲区
            byteBuffer.put(str.getBytes());
            // 读写转换
            byteBuffer.flip();
            // 将缓冲区数据写入channel
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
