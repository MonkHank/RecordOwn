package com.monk.opengl.video

/**
 * 解码状态
 * @author monk
 * @since 2023/11/9 10:21 星期四
 */
enum class DecodeState {
  /**开始状态*/
  START,
  /**解码中*/
  DECODING,
  /**解码暂停*/
  PAUSE,
  /**正在快进*/
  SEEKING,
  /**解码完成*/
  FINISH,
  /**解码器释放*/
  STOP
}