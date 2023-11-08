package com.monk.modulegl

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.monk.commonutils.l
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * @author monk
 * @since 2023/10/26 11:13
 */
object GLUtils {

  /**
   * 编译、链接顶点着色器和片段着色器到program上
   */
  fun creteProgram(vertexSource: String, fragmentSource: String): Int {
    val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)
    if (vertexShader == 0) return 0

    val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource)
    if (fragmentShader == 0) return 0

    //创建可以附加着色器对象的program
    val glCreateProgram: Int = GLES20.glCreateProgram()
    checkGLError("glCreateProgram")
    if (glCreateProgram == 0) l.e("Could not create program")

    //把着色器附着到program上
    GLES20.glAttachShader(glCreateProgram, vertexShader)
    checkGLError("glAttachShader")
    GLES20.glAttachShader(glCreateProgram, fragmentShader)
    checkGLError("glAttachShader")
    GLES20.glLinkProgram(glCreateProgram)
    val linkStatus = IntArray(1)
    GLES20.glGetProgramiv(glCreateProgram, GLES20.GL_LINK_STATUS, linkStatus, 0)
    if (linkStatus[0] != GLES20.GL_TRUE) {
      l.e("Could not link program : ${GLES20.glGetProgramInfoLog(glCreateProgram)} ")
      GLES20.glDeleteProgram(glCreateProgram)
      return 0
    }
    //返回program指针
    return glCreateProgram
  }

  /**
   * 创建纹理,绑定纹理,设置纹理 (简单理解，纹理相当于一张图片)
   */
  fun createTextures(textureTarget: Int): Int {
    val textures = IntArray(1)
    GLES20.glGenTextures(1, textures, 0)
    checkGLError("glGenTextures")

    GLES20.glBindTexture(textureTarget, textures[0])
    checkGLError("glBindTexture = ${textures[0]}")

    //设置纹理的缩小过滤类型
    GLES20.glTexParameterf(textureTarget, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
    //设置纹理的放大过滤类型
    GLES20.glTexParameterf(textureTarget, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
    //设置纹理的X方向边缘环绕
    GLES20.glTexParameteri(textureTarget, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
    //设置纹理的Y方向边缘环绕
    GLES20.glTexParameteri(textureTarget, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
    checkGLError("glTexParameter")

    //返回纹理指针
    return textures[0]
  }

  /**
   * 将顶点数据上传到缓冲区
   */
  fun createFloatBuffer(coordinates: FloatArray): FloatBuffer {
    val floatBuffer: FloatBuffer = ByteBuffer.allocateDirect(coordinates.size * 4)
      .order(ByteOrder.nativeOrder())
      .asFloatBuffer()
    floatBuffer.put(coordinates)
    floatBuffer.position(0)
    return floatBuffer
  }

  var IDENTITY_MATRIX: FloatArray? = null

  init {
    IDENTITY_MATRIX = FloatArray(16)
    Matrix.setIdentityM(IDENTITY_MATRIX,0)
  }

  /**
   * 啥玩意？
   */
  fun changeMvpMatrixInside(width: Float, height: Float,
                            originWidth: Float, originHeight: Float): FloatArray {
    val scale: Float = width * originHeight / height / originWidth
    val mvp: FloatArray? = IDENTITY_MATRIX?.copyOf(IDENTITY_MATRIX!!.size)
    Matrix.scaleM(mvp, 0, if (scale > 1) 1f / scale else 1f, if (scale > 1) 1f else scale, 1f)
    return mvp!!
  }

  /**
   * 啥玩意？
   */
  fun changeMvpMatrixCrop(width: Float, height: Float,
                          originWidth: Float, originHeight: Float): FloatArray {
    val scale: Float = width * originHeight / height / originWidth
    val mvp: FloatArray? = IDENTITY_MATRIX?.copyOf(IDENTITY_MATRIX!!.size)
    Matrix.scaleM(mvp, 0, if (scale > 1) 1f else 1f / scale, if (scale > 1) scale else 1f, 1f)
    return mvp!!
  }

  fun deleteTextures(textureId: IntArray?) {
    if (textureId != null && textureId.isNotEmpty()) {
      GLES20.glDeleteTextures(textureId.size, textureId, 0)
    }
  }

  /**
   * Prefer OpenGL ES 3.0, otherwise 2.0
   *
   * @param context
   * @return
   */
  fun getSupportGlVersion(context: Context): Int {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val configurationInfo = activityManager.deviceConfigurationInfo
    val version = if (configurationInfo.reqGlEsVersion >= 0x30000) 3 else 2
    val glEsVersion = configurationInfo.glEsVersion
    l.d("reqGlEsVersion: ${Integer.toHexString(configurationInfo.reqGlEsVersion)}, " +
      "glEsVersion: $glEsVersion, " +
      "return: $version"
    )
    return version
  }


  fun checkGLError(op: String) {
    val glGetError: Int = GLES20.glGetError()
    if (glGetError != GLES20.GL_NO_ERROR)
      l.i("$op : glError 0x ${Integer.toHexString(glGetError)}")
  }


  private fun loadShader(shapeType: Int, source: String): Int {
    //创建着色器
    val glCreateShader: Int = GLES20.glCreateShader(shapeType)
    checkGLError("glCreateShader type = $shapeType")

    //加载和编译着色器
    GLES20.glShaderSource(glCreateShader, source)
    GLES20.glCompileShader(glCreateShader)

    //检测编译是否成功
    val compiled = IntArray(1)
    GLES20.glGetShaderiv(glCreateShader, GLES20.GL_COMPILE_STATUS, compiled, 0)
    if (compiled[0] == 0) {
      l.e("could not compile shader $shapeType --- ${GLES20.glGetShaderInfoLog(glCreateShader)}")
      GLES20.glDeleteShader(glCreateShader)
      return 0
    }

    //返回着色器指针 (由于gl是c方法, 它的返回都是指针, 对应Java来说用int表示它的指针)
    return glCreateShader
  }

}

