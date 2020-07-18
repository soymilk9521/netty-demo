package com.lr.nio;

import java.io.FileInputStream;
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
 * @since 2020/07/18 11:03
 */
public class NIOFileChannel03 {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("file01.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");) {
            final FileChannel inChannel = fileInputStream.getChannel(); // 创建读取通道
            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true) { // 循环读取
                byteBuffer.clear(); // ******情况buffer*****
                final int read = inChannel.read(byteBuffer);
                if (read == -1) { // 表示读完
                    break;
                }
                byteBuffer.flip(); // 读写切换
                final FileChannel outChannel = fileOutputStream.getChannel(); // 创建写入通道
                outChannel.write(byteBuffer); // 将buffer中的数据写入到outFileChannel --> file02.txt
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
