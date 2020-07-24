package com.lr.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/24 8:33
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel -> ServerSocket
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 获得一个Selector对象
        final Selector selector = Selector.open();
        // 绑定6666端口，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 把serverSocketChannel注册到selector中，监听OP_ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 循环等待客户端连接
        while (true){
            // 等待1s，如果没有事件发生，返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1s， 无连接...");
                continue;
            }

            // 如果返回的 > 0，就获取相关的selectionKey集合
            // 1. 如果返回的 > 0， 表示已经获取到关注的事件
            // 2. selector.selectionKey() 返回关注的事件的集合
            // 通过selectionKeys反向获取通道
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                // 获取SelectionKey
                final SelectionKey key = iterator.next();
                // 根据key对应的通道发生的事件做相应处理
                if (key.isAcceptable()){ // 如果是OP_ACCEPT， 有新的客户端连接
                    // 获取SocketChannel
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将socketChannel设置为非阻塞模式
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成一个socketChannel" + socketChannel.hashCode());
                    // 将socketChannel注册selector, 监听OP_READ事件，同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){
                    // 根据key反向获取channel
                    final SocketChannel channel = (SocketChannel)key.channel();
                    // 获取到该channel关联的Buffer
                    final ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端： " + new String(buffer.array()));
                }
                // 手动从集合中移除selectionKey，防止重复操作
                iterator.remove();
            }
        }
    }

}
