package com.monk.commonutils

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 线程管理器
 * @author monk
 * @date 2018-7-25
 */
object ThreadManager {
    private var mThreadPool: ThreadPool? = null
    val threadPool: ThreadPool?
        get() {
            if (mThreadPool == null) {
                synchronized(ThreadManager::class.java) {
                    if (mThreadPool == null) {
                        val threadCount = 10
                        val cpuCount = Runtime.getRuntime().availableProcessors()
                        mThreadPool = ThreadPool(cpuCount + 1, threadCount, 1L)
                    }
                }
            }
            return mThreadPool
        }

    class ThreadPool(private val corePoolSize: Int, private val maximumPoolSize: Int, private val keepAliveTime: Long) {
        private var executor: ThreadPoolExecutor? = null
        fun execute(r: Runnable?) {
            if (executor == null) {
                executor = ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        TimeUnit.SECONDS, LinkedBlockingQueue(), Executors.defaultThreadFactory(), ThreadPoolExecutor.AbortPolicy())
            }
            executor!!.execute(r)
        }

        fun cancel(r: Runnable?) {
            if (executor != null) {
                executor!!.queue.remove(r)
            }
        }
    }
}