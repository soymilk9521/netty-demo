package com.lr.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/16 18:32
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 使用线程池机制
        // 1. 创建一个线程池
        // 2. 如果客户端有连接，就创建一个线程，与之通信

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 创建并启动ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("socket服务启动.");

        while (true){ // 循环读取多个客户端连接， 如果注释掉只能连接一个客户端。

            System.out.println("等待客户端连接...");
            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept();

            // 如果客户端有连接，就创建一个线程，与之通信
//            executorService.execute(() -> handler(socket));
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("连接到一个客户端.");
                    System.out.println("开启线程： id=" + Thread.currentThread().getId() + "， name=" + Thread.currentThread().getName());
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {

            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            int len;
            while ((len = inputStream.read(bytes)) != -1){
                System.out.print("工作线程： id=" + Thread.currentThread().getId() + "， 名字=" + Thread.currentThread().getName() +"， ");
                System.out.println("数据读取中...");
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和客户端连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }

    }
}
