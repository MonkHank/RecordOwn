package com.monk.modulegl

import android.os.SystemClock

/**
 * @author monk
 * @since 2023/10/26 19:18
 */
object LimitFpsUtil {
  private const val DEFAULT_FPS = 30
  private var frameStartTimeMs: Long = 0
  private const val expectedFrameTimeMs: Long = (1000 / DEFAULT_FPS).toLong()

  fun limitFrameRate() {
    val elapsedFrameTimeMs: Long = SystemClock.elapsedRealtime() - frameStartTimeMs
    val timeToSleepMs: Long = LimitFpsUtil.expectedFrameTimeMs - elapsedFrameTimeMs
    if (timeToSleepMs > 0) {
      SystemClock.sleep(timeToSleepMs)
    }
    frameStartTimeMs = SystemClock.elapsedRealtime()
  }
}