package com.lr.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/24 9:14
 */
public class NIOClient {
    public static void main(String[] args) {
        // 获取一个网络通道
        try (SocketChannel socketChannel = SocketChannel.open()) {
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // 提供服务器端ip和端口
            final InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
            // 连接服务器
            if (!socketChannel.connect(inetSocketAddress)){
                while (!socketChannel.finishConnect()){
                    System.out.println("因为连接需要事件， 客户端不会阻塞，可以做其他工作...");
                }
            }
            String sendMsg = "hello, 尚硅谷";
            // Wraps a byte array into a buffer.
            final ByteBuffer buffer = ByteBuffer.wrap(sendMsg.getBytes());
            // 发送数据，将buffer写入channel
            socketChannel.write(buffer);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
