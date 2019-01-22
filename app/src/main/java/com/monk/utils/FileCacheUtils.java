package com.monk.utils;

import com.alibaba.fastjson.util.IOUtils;
import com.monk.global.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 最好还是单例，因为还要设置有效时间，静态的话就不能设置时间了；
 * @author Kevin
 * @date 2017/8/15.
 */
public class FileCacheUtils {
    private static final String tag = "CacheUtils";

    private static long deadTime = System.currentTimeMillis() + 30 * 60 * 1000;

    public static void setValidTime(long validTime) {
        deadTime = validTime;
    }

    /*** 30分钟有效期*/
    public static void setCache(String url, String json) {
        String cacheDir = MyApplication.mApplication.getCacheDir().getAbsolutePath();
        LogUtil.v(tag, "设置缓存：" + cacheDir + "\t/" + url);
        File cacheFile = new File(cacheDir, url);
        FileWriter fw = null;
        try {
            fw = new FileWriter(cacheFile);
            fw.write(deadTime + "\n");
            if (json != null) {
                fw.write(json);
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fw);
            deadTime = System.currentTimeMillis() + 30 * 60 * 1000;
        }
    }

    /*** 读取写入缓存时候的缓存时间*/
    public static long getCacheDeadTime(String url) {
        File cacheDir = MyApplication.mApplication.getCacheDir();
        File cacheFile = new File(cacheDir, url);
        if (cacheFile.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(cacheFile));
                //把时间读出来
                String deadLine = br.readLine();
                return Long.parseLong(deadLine);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
            }
        }
        return -1;
    }

    /*** 30分钟有效期*/
    public static String getCache(String url) {
        File cacheDir = MyApplication.mApplication.getCacheDir();
        File cacheFile = new File(cacheDir, url);
        if (cacheFile.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(cacheFile));
                //把时间读出来
                String deadLine = br.readLine();
                long deadTime = Long.parseLong(deadLine);
                if (System.currentTimeMillis() < deadTime) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        // 去除时间从第二行开始读
                        sb.append(line);
                    }
                    LogUtil.d(tag, "读缓存:\t" + url);
                    // 不包括时间
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
            }
        }
        return "";
    }

    /*** 没有时间期限的缓存*/
    public static void setCacheNoTime(String url, String text) {
        //  /data/data/包名/cache目录
        String cacheDir = MyApplication.mApplication.getCacheDir().getAbsolutePath();
        LogUtil.v(tag, cacheDir + "\n设置缓存" + url);
        File cacheFile = new File(cacheDir, url);
        FileWriter fw = null;
        try {
            //续写
            fw = new FileWriter(cacheFile);
            fw.write(text);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fw);
        }
    }

    public static String getCacheNoTime(String url) {
        File cacheDir = MyApplication.mApplication.getCacheDir();
        File cacheFile = new File(cacheDir, url);
        if (cacheFile.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(cacheFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                LogUtil.v(tag, "读缓存:" + url);
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(br);
            }
        }
        return "";
    }
}
