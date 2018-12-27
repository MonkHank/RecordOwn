package com.monk.aidldemo.threecache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author monk
 * @date 2018-12-27
 */
public class ThreadPoolManager {
    private static ThreadPool threadPool;

    public static ThreadPool getInstance() {
        if (threadPool == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPool == null) {
                    threadPool=new ThreadPool();
                }
            }
        }
        return threadPool;
    }


    public static class ThreadPool{
        private final ExecutorService executorService;
        ThreadPool() {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }

        public void execute(Runnable r) {
            executorService.execute(r);
        }

        public void cancel() {
            executorService.shutdown();
        }
    }
}
