package com.qpzm7903.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: io_demo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-04-18 22:52
 */

/**
 * 压测结果
 * aPS C:\Windows\system32> sb -u http://localhost:8001/ -c 40 -N 30
 * Starting at 2021/4/18 23:06:31
 * [Press C to stop the test]
 * 35120   (RPS: 979.4)
 * ---------------Finished!----------------
 * Finished at 2021/4/18 23:07:07 (took 00:00:35.9686676)
 * Status 200:    34709
 * Status 303:    411
 *
 * RPS: 1127.7 (requests/second)
 * Max: 245ms
 * Min: 19ms
 * Avg: 28.5ms
 * */
public class HttpServer03_UsingThreadPool {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
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
                final Socket socket = serverSocket.accept();
                executorService.submit(() -> service(socket));
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
