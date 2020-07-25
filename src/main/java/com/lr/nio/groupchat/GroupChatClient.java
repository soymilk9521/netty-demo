package com.lr.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/25 17:24
 */
public class GroupChatClient {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    /**
     * 向服务器发送消息
     * @param msg
     */
    public void sendMsg(String msg) {
        msg = username + " 说： " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            // 获得可用通道
            int count = selector.select();
            if (count > 0) {
                // 遍历SelectionKey集合
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    // 获得SelectionKey
                    SelectionKey selectionKey = iterator.next();
                    // 监听read
                    if (selectionKey.isReadable()) {
                        // 通过SelectionKey获得SocketChannel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        // 创建buffer
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        // 从通道往buffer中读取数据
                        channel.read(byteBuffer);
                        String msg = new String(byteBuffer.array());
                        System.out.println("读到的内容： " + msg);
                    }
                    iterator.remove();
                }

            }else {
               // System.out.println("没有可用通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = new GroupChatClient();
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    chatClient.readMsg();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }

}
