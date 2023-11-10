package com.monk.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.monk.commonutils.l
import com.monk.opengl.video.VideoDrawer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @author monk
 * @since 2023/11/8 15:46
 */
class SimpleRender : GLSurfaceView.Renderer {

  private val drawers = mutableListOf<IDrawer>()

  override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    GLES20.glClearColor(233f, 152f, 0f, 0f)

    //开启混合，即半透明
    GLES20.glEnable(GLES20.GL_BLEND)
    GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

    val textureIds = OpenGLTools.createTextureIds(drawers.size)
    for ((idx, drawer) in drawers.withIndex()) {
      drawer.setTextureID(textureIds[idx])
    }
  }

  override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    l.i("width:$width height:$height")
    GLES20.glViewport(0, 0, width, height)
    for (drawer in drawers) {
      if (drawer is VideoDrawer) {
        drawer.setWorldSize(width, height)
        break
      }
    }
  }

  override fun onDrawFrame(gl: GL10?) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
    drawers.forEach {
      it.draw()
    }
  }

  fun addDrawer(drawer: IDrawer) {
    drawers.add(drawer)
  }
}
