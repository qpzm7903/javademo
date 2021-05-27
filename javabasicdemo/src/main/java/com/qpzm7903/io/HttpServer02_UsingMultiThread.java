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

/**
 * 压测结果
 * PS C:\Windows\system32> sb -u http://localhost:8001/ -c 40 -N 30
 * Starting at 2021/4/18 23:01:27
 * [Press C to stop the test]
 * 34224   (RPS: 951.6)
 * ---------------Finished!----------------
 * Finished at 2021/4/18 23:02:03 (took 00:00:36.0379624)
 * Status 200:    33847
 * Status 303:    381
 *
 * RPS: 1100.9 (requests/second)
 * Max: 174ms
 * Min: 20ms
 * Avg: 29.7ms
 *
 *   50%   below 28ms
 *   60%   below 29ms
 *   70%   below 31ms
 *   80%   below 35ms
 *   90%   below 37ms
 *   95%   below 41ms
 *   98%   below 57ms
 *   99%   below 61ms
 * 99.9%   below 120ms
 *
 * */
public class HttpServer02_UsingMultiThread {
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
                final Socket socket = serverSocket.accept();
                new Thread(() -> {
                    service(socket);
                }).start();
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
