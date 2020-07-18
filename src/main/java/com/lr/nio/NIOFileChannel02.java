package com.lr.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/18 10:52
 */
public class NIOFileChannel02 {
    public static void main(String[] args) {
        // 创建文件对象
        File file = new File("file01.txt");
        // 读取文件数据到输入流
        try (FileInputStream inputStream = new FileInputStream(file)) {
            // 获取FileChannel通道
            FileChannel channel = inputStream.getChannel();
            // 创建缓冲区，指定缓冲区长度
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
            // 从通道中读取数据到缓冲区
            channel.read(byteBuffer);
            // 输入到控制台
            System.out.println(new String(byteBuffer.array()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
