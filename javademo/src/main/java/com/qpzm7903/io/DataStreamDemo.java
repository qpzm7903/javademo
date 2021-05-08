package com.qpzm7903.io;

import java.io.*;

public class DataStreamDemo {

    public static void main(String[] args) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("data.txt"));) {

            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeInt(1);
            dataOutputStream.writeChar('a');
            dataOutputStream.writeChars("testString");
            dataOutputStream.writeByte(2);
            dataOutputStream.writeBytes("123123");
            dataOutputStream.writeDouble(123D);
            dataOutputStream.writeFloat(123F);
            dataOutputStream.writeLong(123L);
        }
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream("data.txt"));) {
            System.out.println(dataInputStream.readBoolean());
            System.out.println(dataInputStream.readInt());
            System.out.println(dataInputStream.readChar());
        }

    }
}
