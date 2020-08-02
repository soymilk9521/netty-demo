package com.lr.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/08/02 14:45
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String filename = "webedi-master.zip";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        // 在linux下， 一个transferTo方法可以完成传输
        // 在window下， 一次调用transferTo方法只能发送8M， 就需要分段传输文件，而且要主要传输时的位置
        // transferTo底层是零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("NIO发送数据的总字节数： " + transferCount + ", 耗时： " + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
