package com.monk.modulegl

import android.opengl.GLES20
import java.nio.FloatBuffer

/**
 * @author monk
 * @since 2023/10/26 11:09
 */
class GLProgram(
  vertexShader: String = VERTEX_SHADER,
  fragmentShader: String = FRAGMENT_SHADER_2D,
  vertexCoordinate: FloatArray = FULL_RECTANGLE_COORDINATE,
  texCoordinate: FloatArray = FULL_RECTANGLE_TEX_COORDINATE
) {

  companion object {
    /**
     * 顶点着色器代码
     */
    private const val VERTEX_SHADER =
      "uniform mat4 uMVPMatrix;\n" +
        "uniform mat4 uTexMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "attribute vec4 aTextureCoord;\n" +
        "varying vec2 vTextureCoord;\n" +
        "void main() {\n" +
        "    gl_Position = uMVPMatrix * aPosition;\n" +
        "    vTextureCoord = (uTexMatrix * (aTextureCoord - 0.5) + 0.5).xy;\n" +
        "}\n"

    /**
     * 片段着色器代码
     */
    private const val FRAGMENT_SHADER_2D =
      "precision mediump float;\n" +
        "varying vec2 vTextureCoord;\n" +
        "uniform sampler2D sTexture;\n" +
        "void main() {\n" +
        "    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
        "}\n"

    /**
     * 顶点坐标数据
     */
    private val FULL_RECTANGLE_COORDINATE = floatArrayOf(
      -1.0f, -1.0f,  // 0 bottom left
      1.0f, -1.0f,  // 1 bottom right
      -1.0f, 1.0f,  // 2 top left
      1.0f, 1.0f
    )

    /**
     * 啥玩意？
     */
    private val FULL_RECTANGLE_TEX_COORDINATE = floatArrayOf(
      0.0f, 0.0f,  // 0 bottom left
      1.0f, 0.0f,  // 1 bottom right
      0.0f, 1.0f,  // 2 top left
      1.0f, 1.0f // 3 top right
    )

    const val COORDINATE_PER_VERTEX = 2
    const val TEXTURE_COORDINATE_STRIDE = 2 * 4
    const val VERTEX_STRIDE = COORDINATE_PER_VERTEX * 4
  }

  var creteProgramId: Int = 0
  var vertexFloatBuffer: FloatBuffer
  var vertexCount: Int = 0
  var texFloatBuffer: FloatBuffer
  var aPositionLoc: Int = 0
  var aTextureCoordinateLoc: Int = 0
  var uMVPMatrixLoc: Int = 0
  var uTexMatrixLoc: Int = 0

  init {
    creteProgramId = GLUtils.creteProgram(vertexShader, fragmentShader)
    vertexFloatBuffer = GLUtils.createFloatBuffer(vertexCoordinate)
    vertexCount = vertexCoordinate.size / COORDINATE_PER_VERTEX
    texFloatBuffer = GLUtils.createFloatBuffer(texCoordinate)
    aPositionLoc = GLES20.glGetAttribLocation(creteProgramId, "aPosition");
    aTextureCoordinateLoc = GLES20.glGetAttribLocation(creteProgramId, "aTextureCoord")
    uMVPMatrixLoc = GLES20.glGetUniformLocation(creteProgramId, "uMVPMatrix")
    uTexMatrixLoc = GLES20.glGetUniformLocation(creteProgramId, "uTexMatrix")
  }

  fun release() {
    GLES20.glDeleteProgram(creteProgramId)
    creteProgramId = 0
  }

}