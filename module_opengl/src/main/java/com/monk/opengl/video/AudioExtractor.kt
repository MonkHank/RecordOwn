package com.monk.opengl.video

import android.media.MediaFormat
import java.nio.ByteBuffer

/**
 * 音频数据提取器
 * @author monk
 * @since 2023/11/9 10:25 星期四
 */
class AudioExtractor(path: String): IExtractor {

  private val mMediaExtractor = MMExtractor(path)

  override fun getFormat(): MediaFormat? {
    return mMediaExtractor.getAudioFormat()
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