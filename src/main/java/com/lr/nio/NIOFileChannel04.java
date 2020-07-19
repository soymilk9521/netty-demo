package com.lr.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/18 11:54
 */
public class NIOFileChannel04 {
    public static void main(String[] args) {
        final File file = new File("C:\\Users\\bigtiger\\Desktop\\temp\\bus.png");
        try (FileInputStream fileInputStream = new FileInputStream(file);
             FileOutputStream fileOutputStream = new FileOutputStream("bus.png");) {
            final FileChannel source = fileInputStream.getChannel(); // 创建读取通道
            final FileChannel target = fileOutputStream.getChannel(); // 创建写入通道
            target.transferFrom(source, 0, source.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
