package com.monk.threecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.ImageView;

import com.monk.commonutils.ThreadPoolManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author monk
 * @date 2018-12-27
 */
public class BitmapCacheUtil {
    private volatile static BitmapCacheUtil httpUtil;
    private ImageCache imageCache;
    private Context context;

    private BitmapCacheUtil(Context context) {
        this.context=context;
        Map<String, SoftReference<Bitmap>> cacheMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            imageCache = new ImageCache(cacheMap);
        }
    }

    public BitmapCacheUtil getInstance(Context context){
        synchronized (this) {
            if (httpUtil == null) {
                synchronized (this) {
                    httpUtil = new BitmapCacheUtil(context);
                }
            }
        }
        return httpUtil;
    }

    private byte[] getBytesFromWeb(String path) {
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
                in = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[]temp = new byte[1024];
                int length;
                while ((length = in.read(temp)) != -1) {
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

    private void writeFileToStorage(String fileName, byte[] b) {
        FileOutputStream fos=null;
        File file = new File(context.getFilesDir(),fileName);
        try {
            fos = new FileOutputStream(file);
            fos.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] readBytesFromStorage(String fileName) {
        byte[]b=null;
        FileInputStream fis=null;
        ByteArrayOutputStream baos=null;
        try {
            fis = context.openFileInput(fileName);
            baos = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int len;
            while ((len = fis.read(temp)) != -1) {
                baos.write(temp,0,len);
            }
            b=baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
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

    /**
     * 将图片添加到缓存中
     * @param fileName
     * @param data
     */
    private void putBitmapIntoCache(String fileName, byte[] data) {
        writeFileToStorage(fileName, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // 将图片存入强引用
            imageCache.put(fileName, BitmapFactory.decodeByteArray(data, 0, data.length));
        }
    }

    /**
     * 从缓存中取出图片
     * @param fileName
     * @return
     */
    private Bitmap getBitmapFromCache(String fileName) {
        // 从强引用中取出图片
        Bitmap bm=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bm = imageCache.get(fileName);
            if (bm == null) {
                Map<String, SoftReference<Bitmap>> cacheMap = imageCache.getCacheMap();
                SoftReference<Bitmap> softReference = cacheMap.get(fileName);
                if (softReference != null) {
                    bm=softReference.get();
                    imageCache.put(fileName, bm);
                }else {
                    byte[] data = readBytesFromStorage(fileName);
                    if (data != null && data.length > 0) {
                        bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                        imageCache.put(fileName, bm);
                    }
                }
            }
        }
        return bm;
    }

    /**
     * 使用三级缓存为ImageView设置图片(LruCache,SoftReference,FileDir)
     * @param path
     * @param view
     */
    public void setImageToView(final String path, final ImageView view) {
        final String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
        Bitmap bm = getBitmapFromCache(fileName);
        if (bm != null) {
            view.setImageBitmap(bm);
        }else {
            ThreadPoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    byte[] b = getBytesFromWeb(path);
                    if (b != null && b.length > 0) {
                        // 将图片字节数组写入到缓存中
                        putBitmapIntoCache(fileName, b);
                        final Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                view.setImageBitmap(bm);
                            }
                        });
                    }
                }
            });
        }
    }
}
