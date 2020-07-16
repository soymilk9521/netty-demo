package com.lr.bio;

import java.io.*;

/**
 * <p>
 *
 * </p>
 *
 * @author LR
 * @since 2020/07/16 18:55
 */
public class TryWithResource {
    public static void main(String[] args) {
        try (BufferedInputStream bin = new BufferedInputStream(TryWithResource.class.getClassLoader().getResourceAsStream("test1.txt"));
        //try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File("test2.txt")));
             BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File("out.txt")))) {
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
