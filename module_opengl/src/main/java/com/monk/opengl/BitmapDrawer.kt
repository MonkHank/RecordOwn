package com.monk.opengl

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils

/**
 * @author monk
 * @since 2023/11/8 16:04
 */
class BitmapDrawer(private var mTextureId: Int, private val mBitmap: Bitmap) : IDrawer {

  //-------【注1：坐标变更了，由四个点组成一个四边形】-------
  // 顶点坐标
  private val mVertexCoors = floatArrayOf(
    -1f, -1f,
    1f, -1f,
    -1f, 1f,
    1f, 1f
  )

  // 纹理坐标
  private val mTextureCoors = floatArrayOf(
    0f, 1f,
    1f, 1f,
    0f, 0f,
    1f, 0f
  )

  //OpenGL程序ID
  private var mProgram: Int = -1

  // 顶点坐标接收者
  private var mVertexPosHandler: Int = -1
  // 纹理坐标接收者
  private var mTexturePosHandler: Int = -1

  //-------【注2：新增纹理接收者】-------
  // 纹理接收者
  private var mTextureHandler: Int = -1

  override fun draw() {
    if (mTextureId != -1) {
      //【步骤2: 创建、编译并启动OpenGL着色器】
      createGLPrg()
      //-------【注4：新增两个步骤】-------
      //【步骤3: 激活并绑定纹理单元】
      activateTexture()
      //【步骤4: 绑定图片到纹理单元】
      bindBitmapToTexture()
      //----------------------------------
      //【步骤5: 开始渲染绘制】
      doDraw()
    }
  }

  override fun setTextureID(id: Int) {
    mTextureId = id
  }

  override fun release() {
    GLES20.glDisableVertexAttribArray(mVertexPosHandler)
    GLES20.glDisableVertexAttribArray(mTexturePosHandler)
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    GLES20.glDeleteTextures(1, intArrayOf(mTextureId), 0)
    GLES20.glDeleteProgram(mProgram)
  }

  private fun createGLPrg() {
    if (mProgram == -1) {
      //省略与绘制三角形一致的部分
      //......

      mVertexPosHandler = GLES20.glGetAttribLocation(mProgram, "aPosition")
      mTexturePosHandler = GLES20.glGetAttribLocation(mProgram, "aCoordinate")
      //【注3：新增获取纹理接收者】
      mTextureHandler = GLES20.glGetUniformLocation(mProgram, "uTexture")
    }
    //使用OpenGL程序
    GLES20.glUseProgram(mProgram)
  }

  private fun activateTexture() {
    //激活指定纹理单元
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
    //绑定纹理ID到纹理单元
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId)
    //将激活的纹理单元传递到着色器里面
    GLES20.glUniform1i(mTextureHandler, 0)
    //配置边缘过渡参数
    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
  }

  private fun bindBitmapToTexture() {
    if (!mBitmap.isRecycled) {
      //绑定图片到被激活的纹理单元
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0)
    }
  }

  private fun doDraw() {
    //省略与绘制三角形一致的部分
    //......

    //【注5：绘制顶点加1，变为4】
    //开始绘制：最后一个参数，将顶点数量改为4
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
  }

  private fun getVertexShader(): String {
    return "attribute vec4 aPosition;" +
      "attribute vec2 aCoordinate;" +
      "varying vec2 vCoordinate;" +
      "void main() {" +
      "  gl_Position = aPosition;" +
      "  vCoordinate = aCoordinate;" +
      "}"
  }

  private fun getFragmentShader(): String {
    return "precision mediump float;" +
      "uniform sampler2D uTexture;" +
      "varying vec2 vCoordinate;" +
      "void main() {" +
      "  vec4 color = texture2D(uTexture, vCoordinate);" +
      "  gl_FragColor = color;" +
      "}"
  }

  //省略和绘制三角形内容一致的部分
  //......
}
