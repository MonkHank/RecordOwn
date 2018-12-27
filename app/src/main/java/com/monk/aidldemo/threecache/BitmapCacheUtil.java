package com.monk.aidldemo.threecache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author monk
 * @date 2018-12-27
 */
public class BitmapCacheUtil {
    private volatile static BitmapCacheUtil httpUtil;

    private BitmapCacheUtil() {
    }

    public BitmapCacheUtil getInstance(){
        synchronized (this) {
            if (httpUtil == null) {
                synchronized (this) {
                    httpUtil = new BitmapCacheUtil();
                }
            }
        }
        return httpUtil;
    }

    public byte[] getBytesFromWeb(String path) {
        URL url;
        ByteArrayOutputStream baos = null;
        InputStream in= null;
        byte []b = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                baos = new ByteArrayOutputStream();
                byte[]temp = new byte[1024];
                int length;
                while ((length = in.read(temp)) != 0) {
                    baos.write(temp,0,length);
                }
                b= baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return b;
    }

    
}
