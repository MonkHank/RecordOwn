package com.monk.modulegl

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.view.Surface
import androidx.annotation.RequiresApi
import com.monk.modulegl.PlayerListener.Companion.MEDIA_INFO_URL_NULL
import com.monk.modulegl.PlayerListener.Companion.TYPE_UNEXPECTED

/**
 * @author monk
 * @since 2023/10/26 15:33
 */
class AndroidMediaPlayer(private var context: Context) {

  private lateinit var mediaPlayer: MediaPlayer

  private val playerListener = PlayerListener()

  private var mIsPreparing = false

  private var bufferingPercent = 0

  val duration: Int
    get() = mediaPlayer.duration

  val currentPosition:Long
    get() = mediaPlayer.currentPosition.toLong()

  init {
    if (context !is Application) context = context.applicationContext
  }

  fun initPlayer(): AndroidMediaPlayer {
    mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
    mediaPlayer.setOnErrorListener { _, what, extra ->
      errorMsg("player error! - what:$what - extra:$extra")
      true
    }
    mediaPlayer.setOnCompletionListener { playerListener.onCompletion.invoke() }
    mediaPlayer.setOnInfoListener { _, what, extra ->
      //解决MEDIA_INFO_VIDEO_RENDERING_START多次回调问题
      if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
        if (mIsPreparing) {
          playerListener.onInfo.invoke(what, extra)
          mIsPreparing = false
        } else {
          playerListener.onInfo.invoke(what, extra)
        }
      }
      true
    }
    mediaPlayer.setOnBufferingUpdateListener { _, percent -> bufferingPercent = percent }
    mediaPlayer.setOnPreparedListener { playerListener.onPrepared.invoke() }
    mediaPlayer.setOnVideoSizeChangedListener { _, width, height -> playerListener.onVideoSizeChanged.invoke(width, height) }
    return this
  }

  fun setSurface(surface: Surface): AndroidMediaPlayer {
    kotlin.runCatching { mediaPlayer.setSurface(surface) }
      .onFailure { e -> errorMsg(e.message) }
    return this
  }

  fun setDataSource(path: String, headers: Map<String, String>? = null): AndroidMediaPlayer {
    if (path.isEmpty()) {
      playerListener.onInfo.invoke(MEDIA_INFO_URL_NULL, 0)
      return this
    }
    kotlin.runCatching { mediaPlayer.setDataSource(context, Uri.parse(path), headers) }
      .onFailure { e -> errorMsg(e.message, 2) }
    return this
  }

  fun prepareAsync(): AndroidMediaPlayer {
    kotlin.runCatching {
      mIsPreparing = true
      mediaPlayer.prepareAsync()
    }.onFailure { e-> errorMsg(e.message)}
    return this
  }

  fun start() {
    kotlin.runCatching { mediaPlayer.start() }
      .onFailure { e -> errorMsg(e.message) }
  }

  fun pause() {
    kotlin.runCatching { mediaPlayer.pause() }
      .onFailure { e -> errorMsg(e.message) }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  fun seekTo(millSecond: Long, mode: Int = MediaPlayer.SEEK_PREVIOUS_SYNC /* mode */) {
    kotlin.runCatching { mediaPlayer.seekTo(millSecond, mode) }
      .onFailure { e -> errorMsg(e.message) }
  }

  fun setPlayerListener(listener: PlayerListener.() -> Unit): AndroidMediaPlayer {
    playerListener.let { listener }
    return this
  }

  private fun errorMsg(error: String?, type: Int = TYPE_UNEXPECTED) {
    playerListener.onError(type, "player error! - $error")
  }

}

class PlayerListener {
  companion object {
    const val MEDIA_INFO_URL_NULL = -1
    const val TYPE_UNEXPECTED = 3
  }


  var onError: (type: Int, error: String) -> Unit = { _: Int, _: String -> }

  var onCompletion: () -> Unit = {}

  /**
   * 视频信息
   */
  var onInfo: (what: Int, extra: Int) -> Unit = { _, _ -> }

  var onPrepared: () -> Unit = {}

  /**
   * 视频大小变化
   */
  var onVideoSizeChanged: (width: Int, height: Int) -> Unit = { _, _ -> }
}