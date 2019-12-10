package com.monk.sockets;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.monk.commonutils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author monk
 * @date 2019/12/9
 */
public class SocketClient {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 55533;

        Socket socket = new Socket(host, port);
        OutputStream out = socket.getOutputStream();
        out.write("客户端写了一些东西".getBytes(StandardCharsets.UTF_8));
        socket.shutdownOutput();

        InputStream in = socket.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte [] bytes=new byte[1024];
        int len;
        while((len = in.read(bytes, 0, bytes.length)) !=-1)
            sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
        System.out.println("从客户端获取的消息 = "+sb);

        IOUtils.close(in);
        IOUtils.close(out);
        socket.close();
    }
}
