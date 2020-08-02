package com.lr.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/08/02 14:37
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(address);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("客户端连接成功...");
            int read = 0;
            while (-1 != read) {
                try {
                    read = socketChannel.read(byteBuffer);
                } catch (IOException e) {
                    break;
                }
                byteBuffer.rewind(); // 倒带， position=0， mark作废
            }
        }
    }
}
