package com.qpzm7903.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: io_demo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-18 22:52
 */

public class HttpServer01_SingleThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8001);
        while (true) {
            try {

                // wait for access
                // 访问 localhost:8001 即可
                // sb -u http://localhost:8001/ -c 40 -N 30
                // 结果 RPS: 32.8 (requests/second)
                //Max: 1230ms
                //Min: 31ms
                //Avg: 1156.8ms
                Socket accept = serverSocket.accept();
                service(accept);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            // 模拟 io操作
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            // 输出报文头
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello,nio");
            printWriter.close();
            socket.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
