package com.monk.opengl.video

import android.media.MediaFormat
import com.monk.commonutils.l
import com.monk.opengl.video.VideoExtractor.Const.TAG
import java.nio.ByteBuffer

/**
 * 视频数据提取器
 * @author monk
 * @since 2023/11/9 10:23 星期四
 */
class VideoExtractor(path: String) : IExtractor {

  object Const {
    const val TAG = "VideoExtractor"
  }

  private val mMediaExtractor = MMExtractor(path)

  override fun getFormat(): MediaFormat? {
    val videoFormat = mMediaExtractor.getVideoFormat()
    l.i(TAG,"videoFormat:$videoFormat")
    return videoFormat
  }

  override fun readBuffer(byteBuffer: ByteBuffer): Int {
    return mMediaExtractor.readBuffer(byteBuffer)
  }

  override fun getCurrentTimestamp(): Long {
    return mMediaExtractor.getCurrentTimestamp()
  }

  override fun getSampleFlag(): Int {
    return mMediaExtractor.getSampleFlag()
  }

  override fun seek(pos: Long): Long {
    return mMediaExtractor.seek(pos)
  }

  override fun setStartPos(pos: Long) {
    return mMediaExtractor.setStartPos(pos)
  }

  override fun stop() {
    mMediaExtractor.stop()
  }
}