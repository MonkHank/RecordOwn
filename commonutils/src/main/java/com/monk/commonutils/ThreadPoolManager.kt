package com.monk.commonutils

import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author monk
 * @since 2018-12-27
 */
object ThreadPoolManager {
    private var threadPool: ThreadPool? = null
    val instance: ThreadPool?
        get() {
            if (threadPool == null) {
                synchronized(ThreadPoolManager::class.java) {
                    if (threadPool == null) {
                        threadPool = ThreadPool()
                    }
                }
            }
            return threadPool
        }

    class ThreadPool internal constructor() {
        private val executorService: ExecutorService
        fun execute(r: Runnable?) {
            executorService.execute(r)
        }

        fun cancel() {
            executorService.shutdown()
        }

        init {
            executorService = ThreadPoolExecutor(0, Int.MAX_VALUE, 0,
                    TimeUnit.SECONDS, LinkedBlockingQueue())
        }
    }
}