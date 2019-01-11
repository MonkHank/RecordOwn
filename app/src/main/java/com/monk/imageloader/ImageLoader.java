package com.monk.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author monk
 * @date 2019-01-07
 */
public class ImageLoader {
    private ExecutorService executors = new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    IImageCache<String,Bitmap> memoryCache = new MemoryCache();

    public Bitmap displayImage(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            return bitmap;
        }else {
            return downloadImage(url);
        }
    }

    private Bitmap downloadImage(String urlPath) {
        URL url ;
        Bitmap bitmap;
        try {
            url = new URL(urlPath);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setReadTimeout(1000);
            httpUrlConn.setDoInput(true);
            httpUrlConn.connect();
            if (httpUrlConn.getResponseCode() == 200) {
                InputStream in = httpUrlConn.getInputStream();
                bitmap=BitmapFactory.decodeStream(in);
                memoryCache.put(urlPath, bitmap);
                return bitmap;
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
