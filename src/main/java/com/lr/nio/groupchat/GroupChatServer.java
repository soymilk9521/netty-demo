package com.lr.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/25 16:07
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final Integer PORT = 6667;

    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 得到ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        try {
            while (true){ // 循环监听
                int count = selector.select(); // 返回监听通道个数
                if (count > 0) {
                    // 遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        // 取得selectionKey
                        SelectionKey selectionKey = iterator.next();
                        // 监听accept
                        if (selectionKey.isAcceptable()){
                            // 取得客户端SocketChannel
                            SocketChannel channel = listenChannel.accept();
                            // 将SocketChannel注册到Selector
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println(channel.getRemoteAddress().toString().substring(1) + " ------> 上线");
                        }
                        // 监听read
                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        // 手动从集合中移除selectionKey，防止重复操作
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 得到SocketChannel
            channel = (SocketChannel)key.channel();
            // 创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 读取数据到buffer
            int read = channel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端： " + msg);
                // 向其他客户端转发消息
                sendMsgToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendMsgToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器发送消息中...");
        // 遍历 所有注册到selector上的SocketChannel，并排除消息发送者自身
        for (SelectionKey key: selector.keys()) {
            // 通过key取得SocketChannel
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel)targetChannel;
                // 将msg存储到buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer中数据写入通道
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }

}
