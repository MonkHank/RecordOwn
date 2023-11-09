package com.monk.opengl.video

/**
 * 解码进度
 * @author monk
 * @since 2023/11/9 10:19 星期四
 */
interface IDecoderProgress {
  /**
   * 视频宽高回调
   */
  fun videoSizeChange(width: Int, height: Int, rotationAngle: Int)

  /**
   * 视频播放进度回调
   */
  fun videoProgressChange(pos: Long)
}