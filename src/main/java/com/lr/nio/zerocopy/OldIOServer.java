package com.lr.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/08/02 13:35
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功...");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[2048];
            long total = 0;
            while (true){
                int read = inputStream.read(bytes, 0, bytes.length);
                if (-1 == read) {
                    System.out.println("数据读取完成！");
                    break;
                }else {
                    total += read;
                }
            }
            System.out.println("接收数据总字节数： "  + total);
        }
    }
}
