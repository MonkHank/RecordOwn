package com.monk.opengl.video

import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import com.monk.commonutils.l
import com.monk.opengl.R
import com.monk.opengl.SimpleRender
import kotlinx.android.synthetic.main.activity_opengl_player.gl_surface
import java.util.concurrent.Executors

/**
 * @author monk
 * @since 2023/11/9 9:21
 */
class OpenGLPlayerActivity: AppCompatActivity() {
  private val path = "/storage/emulated/0/DCIM/FaceUnity/20220905-193219.mp4"
//  private val path = Environment.getExternalStorageDirectory()
//    .absolutePath+"/DCIM/FaceUnity/20220905-193219.mp4"
  private lateinit var drawer: VideoDrawer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_opengl_player)
    initRender()
    l.i("path:$path")
  }

  private fun initRender() {
    drawer = VideoDrawer()
    drawer.setVideoSize(1080,1920)
    drawer.getSurfaceTexture {
      //使用SurfaceTexture初始化一个Surface，并传递给MediaCodec使用
      initPlayer(Surface(it))
    }
    gl_surface.setEGLContextClientVersion(2)
    val render = SimpleRender()
    render.addDrawer(drawer)
    gl_surface.setRenderer(render)
  }

  private fun initPlayer(sf: Surface) {
    val threadPool = Executors.newFixedThreadPool(10)

    val videoDecoder = VideoDecoder(path, null, sf)
    threadPool.execute(videoDecoder)

    val audioDecoder = AudioDecoder(path)
    threadPool.execute(audioDecoder)

    videoDecoder.goOn()
    audioDecoder.goOn()
  }
}
