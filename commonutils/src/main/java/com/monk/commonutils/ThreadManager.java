package com.monk.commonutils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器
 * @author monk
 * @date 2018-7-25
 */
public class ThreadManager {
    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadManager.class) {
                if (mThreadPool == null) {
                    int threadCount = 10;
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    mThreadPool = new ThreadPool(cpuCount+1, threadCount, 1L);
                }
            }
        }
        return mThreadPool;
    }

    public static class ThreadPool {
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new AbortPolicy());
            }
            executor.execute(r);
        }

        public void cancel(Runnable r) {
            if (executor != null) {
                executor.getQueue().remove(r);
            }
        }
    }
}
