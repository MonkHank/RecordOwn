package com.monk.opengl.video

/**
 * 解码状态回调接口
 * @author monk
 * @since 2023/11/9 10:20 星期四
 */
interface IDecoderStateListener {
  fun decoderPrepare(decodeJob: BaseDecoder?)
  fun decoderReady(decodeJob: BaseDecoder?)
  fun decoderRunning(decodeJob: BaseDecoder?)
  fun decoderPause(decodeJob: BaseDecoder?)
  fun decodeOneFrame(decodeJob: BaseDecoder?, frame: Frame)
  fun decoderFinish(decodeJob: BaseDecoder?)
  fun decoderDestroy(decodeJob: BaseDecoder?)
  fun decoderError(decodeJob: BaseDecoder?, msg: String)
}