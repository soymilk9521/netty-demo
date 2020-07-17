package com.lr.nio;

import java.nio.IntBuffer;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/17 8:55
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明buffer的使用
        // 创建一个Buffer，大小为5，即可以存放5个int值

        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存放数据
        for (int i = 0; i <intBuffer.capacity(); i++){
            intBuffer.put(i * 2);
        }

        // 从buffer中读取数据
        // 将buffer转换，读写切换，flip重置指针位初始位置
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get()); //get切换指针位置
        }
    }
}
