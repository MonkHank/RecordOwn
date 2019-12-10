package com.monk.sockets;


import com.monk.commonutils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author monk
 * @date 2019/12/9
 */
public class SocketServer {

    /**
     * 服务端先跑起来，否则客户端无法运行，会抛异常
     */
    public static void main(String[] args) throws IOException {
        int port =55533;
        ServerSocket ss = new ServerSocket(port);

        System.out.println("ServerSocket 一直等待连接");
        Socket socket = ss.accept();// 一直阻塞，直到连接建立

        InputStream in = socket.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte [] bytes=new byte[1024];
        int len;
        while((len = in.read(bytes, 0, bytes.length)) !=-1)
            sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
        System.out.println("从客户端获取的消息 = "+sb);

        OutputStream out = socket.getOutputStream();
        out.write("服务端info".getBytes(Charset.forName("utf-8")));

        IOUtils.close(in);
        IOUtils.close(out);
        socket.close();
        ss.close();
    }
}
