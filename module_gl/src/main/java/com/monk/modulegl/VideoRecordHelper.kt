package com.monk.modulegl

import android.content.Context
import android.media.MediaMetadataRetriever
import android.opengl.Matrix
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File

/**
 * @author monk
 * @since 2023/10/27 9:52
 */
@RequiresApi(Build.VERSION_CODES.P)
class VideoRecordHelper(private val context: Context, srcFilePath: String) {

  init {
    analysisVideo(srcFilePath)
  }

  private val mRecordLock = Any() //录制锁
  private val mVideoEncoder: MediaVideoEncoder? = null

  @Volatile
  private var frameAvailableTime = 0L //录制写入初始时间

  @Volatile
  private var isStopRecording = false //释放需要暂停录制


  /**
   * 写入数据
   *
   * @param texId
   * @param texMatrix
   * @param mvpMatrix
   */
  fun frameAvailableSoon(texId: Int, texMatrix: FloatArray?, mvpMatrix: FloatArray?) {
    synchronized(mRecordLock) {
      if (mVideoEncoder != null) {
        if (frameAvailableTime == 0L) {
          frameAvailableTime = System.currentTimeMillis()
        }
        val matrix: FloatArray = DecimalUtils.copyArray(mvpMatrix)
        when (videoOrientation) {
          270 -> Matrix.rotateM(matrix, 0, 90f, 0f, 0f, 1f)
          180 -> Matrix.rotateM(matrix, 0, 180f, 0f, 0f, 1f)
          90 -> Matrix.rotateM(matrix, 0, 270f, 0f, 0f, 1f)
        }
        mVideoEncoder.frameAvailableSoon(texId, texMatrix, matrix)
        if (!isStopRecording) {
          onVideoRecordingListener?.onProcess?.invoke(System.currentTimeMillis() - frameAvailableTime)
        }
      }
    }
  }





  private var videoOrientation = 0 //文件朝向
  private var calFrameRate = 25

  private fun analysisVideo(srcFilePath: String) {
    val mediaMetadataRetriever = MediaMetadataRetriever()
    try {
      mediaMetadataRetriever.setDataSource(srcFilePath)
      videoOrientation = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)!!.toInt()
      //计算帧率
      val mDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()
      val mFrameCount = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)!!.toInt()
      calFrameRate = (mFrameCount * 1000 / mDuration).toInt()
    } catch (exception: Exception) {
      exception.printStackTrace()
    } finally {
      mediaMetadataRetriever.release()
    }
  }


  private var onVideoRecordingListener: OnVideoRecordingListener? = null

  fun setOnVideoRecordingListener(listener: OnVideoRecordingListener.() -> Unit) {
    onVideoRecordingListener = OnVideoRecordingListener().also(listener)
  }

  class OnVideoRecordingListener {
    var onPrepared: () -> Unit = {}
    var onProcess: (time: Long) -> Unit = {}
    var onRecordFinish: (file: File) -> Unit = {}
  }
}