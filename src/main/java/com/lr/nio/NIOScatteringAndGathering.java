package com.lr.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * <p>
 *  Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入（分散）。
 *  Gathering：从buffer读取数据时，可以采用buffer数组，依次读取（聚集）。
 * </p>
 *
 * @author LR
 * @since 2020/07/19 9:45
 */
public class NIOScatteringAndGathering {
    public static void main(String[] args) throws IOException {
        // ServerSocketChannel 和 socketChannel
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        // 绑定7000端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        final ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接（telnet）
        final SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8; // 假定从客户端接收到8个字节

        while (true){ // 循环读取
            int byteRead = 0;
            while ( byteRead < messageLength) {
                final long read = socketChannel.read(byteBuffers); // 读取到byteBuffers
                byteRead += read;
                System.out.println("read=" + read);
                // 使用流打印，看看当前的这个buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position=" + byteBuffer.position() + ", limit=" + byteBuffer.limit()).forEach(System.out::println);
            }

            // 将所有buffer进行flip
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            // 将数据读出显示到客户端
            int byteWrite = 0;
            while (byteWrite < messageLength) {
                final long write = socketChannel.write(byteBuffers); // 写入到socketChannel
                byteWrite += write;
            }

            // 将所有的buffer进行clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite + ", messageLength=" + messageLength);
        }

    }
}
