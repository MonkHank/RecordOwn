package com.monk.opengl.video

import android.media.MediaCodec
import java.nio.ByteBuffer

/**
 * 一帧数据
 * @author monk
 * @since 2023/11/9 10:21 星期四
 */
class Frame {
  var buffer: ByteBuffer? = null

  private var bufferInfo = MediaCodec.BufferInfo()

  fun setBufferInfo(info: MediaCodec.BufferInfo) {
    bufferInfo.set(info.offset, info.size, info.presentationTimeUs, info.flags)
  }
}