package com.lr.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/08/02 13:40
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7001);
        String filename = "webedi-master.zip";
        InputStream inputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[2048];
        int read;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((read = inputStream.read(bytes)) >= 0) {
            total += read;
            dataOutputStream.write(bytes, 0, read);
        }
        System.out.println("发送数据总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
