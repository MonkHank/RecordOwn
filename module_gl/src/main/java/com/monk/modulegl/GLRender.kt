package com.monk.modulegl

import android.graphics.SurfaceTexture
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Build
import android.view.Surface
import android.view.View
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.monk.commonutils.MD5Utils
import com.monk.commonutils.l
import com.monk.modulegl.GLProgram.Companion.COORDINATE_PER_VERTEX
import com.monk.modulegl.GLProgram.Companion.TEXTURE_COORDINATE_STRIDE
import com.monk.modulegl.GLProgram.Companion.VERTEX_STRIDE
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.properties.Delegates

/**
 * @author monk
 * @since 2023/10/26 11:05
 */
class GLRender(
  private var glSurfaceView: GLSurfaceView,
  private val videoPath: String
) : GLSurfaceView.Renderer {

  /**
   * 着色器program
   */
  private var glProgram: GLProgram? = null

  /**
   * 纹理指针
   */
  private var originalTextureId by Delegates.notNull<Int>()

  /**
   * 为解决某个机型bug,具体可以查看face unity代码提交日志
   */
  private var onSurfaceCreate: Boolean = false

  private val fuRenderInputData = FURenderInputData(0, 0)

  private var surfaceTexture: SurfaceTexture? = null
  private lateinit var surface: Surface

  private lateinit var androidMediaPlayer: AndroidMediaPlayer

  private var outPlayerListener: PlayerListener? = null
  private var glRenderListener: GLRenderListener? = null

  private var playPosition: Long = 0
  private var isCompleted = false

  private var videoOrientation = 0
  private var originalWidth = 0
  private var originalHeight = 0

  private var glSurfaceViewWidth: Int = 1
  private var glSurfaceViewHeight: Int = 1

  private var isActivityPause = false

  /**
   * 渲染模式
   * 0：普通模式
   * 1：导出模式
   * 2：分析视频内容模式
   */
  @Volatile
  private var renderMode: RenderModeEnum = RenderModeEnum.NORMAL
  private var frameInterval = 0L
  private var videoHash = ""

  @Volatile
  private var isLimiterFPS = false

  /** 渲染后纹理结果 **/
  private var faceUnity2DTexId = 0

  private var mDuration = 0L
  private var mFrameCount = 0
  private var frameIndexBeanList = arrayListOf<VideoFrameIndexBean>()


  /**特效处理开关*/
  @Volatile
  private var renderSwitch = true
  private var frameCount = 0//啥玩意？
  private var frameFuRenderMinCount = 0

  /**全身 avatar 相关*/
  private var smallViewportX = 0
  private var smallViewportY = 0
  private val smallViewportBottomPadding = SizeUtils.dp2px(16f)
  private val smallViewportWidth = SizeUtils.dp2px(90f)
  private val smallViewportHorizontalPadding = SizeUtils.dp2px(16f)

  /**换算矩阵*/
  private var defaultFUTexMatrix: FloatArray = MatrixHelper.TEXTURE_MATRIX.copyOf()//默认FURender图形矩阵
  private var originMvpMatrix = MatrixHelper.TEXTURE_MATRIX.copyOf()//原始图形绑定矩阵
  private var smallViewMatrix: FloatArray = MatrixHelper.TEXTURE_MATRIX.copyOf()//小窗图形绑定矩阵
  private var defaultFUMvpMatrix: FloatArray = MatrixHelper.TEXTURE_MATRIX.copyOf()//默认FURender图形矩阵
  private var originTexMatrix = MatrixHelper.TEXTURE_MATRIX.copyOf() //原始图形纹理矩阵
  private var currentFUTexMatrix: FloatArray = MatrixHelper.TEXTURE_MATRIX.copyOf()//最终渲染FURender图形矩阵
  private var currentFUMvpMatrix: FloatArray = MatrixHelper.TEXTURE_MATRIX.copyOf()//最终渲染FURender图形矩阵

  @Volatile
  private var currentFURenderOutputData: FURenderOutputData? = null

  private var externalInputType = FUExternalInputEnum.EXTERNAL_INPUT_TYPE_CAMERA//数据源类型

  init {
    fuRenderInputData.apply {
      texture = FURenderInputData.FUTexture(FUInputTextureEnum.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE, 0)
      renderConfig.apply {
        externalInputType = FUExternalInputEnum.EXTERNAL_INPUT_TYPE_VIDEO
        cameraFacing = CameraFacingEnum.CAMERA_BACK
        inputBufferMatrix = FUTransformMatrixEnum.CCROT0
        inputTextureMatrix = FUTransformMatrixEnum.CCROT0
      }
    }
    externalInputType = FUExternalInputEnum.EXTERNAL_INPUT_TYPE_VIDEO
    glSurfaceView.setEGLContextClientVersion(GLUtils.getSupportGlVersion(FURenderManager.mContext))
    glSurfaceView.setRenderer(this)
    glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    isLimiterFPS = true
    videoHash = MD5Utils.getMD5(videoPath)
    mFURenderKit.enableVideoHash(FURenderConfig.isOpenSetVideoHash)
    analysisVideo()
  }


  fun onResume() {
    if (isActivityPause) {
      glSurfaceView.onResume()
    }
    isActivityPause = false
    if (videoHash.isEmpty()) {
      videoHash = MD5Utils.getMD5(videoPath)
    }
  }

  fun onPause() {
    isActivityPause = true
    val count = CountDownLatch(1)
    glSurfaceView.queueEvent {
      destroyGlSurface()
      count.countDown()
    }
    kotlin.runCatching {
      count.await(500, TimeUnit.MILLISECONDS)
    }
    glSurfaceView.onPause()
  }

  fun onDestroy() {
    releaseMediaPlayer()
    glRenderListener = null
    playPosition = 0

    if (mExtractFrameWorkThread != null) {
      mExtractFrameWorkThread?.stopExtract()
      frameIndexBeanList.clear()
    }
  }

  private fun destroyGlSurface() {
    surfaceTexture?.let {
      it.release()
      surfaceTexture = null
    }
    surface.release()
    if (originalTextureId != 0) {
      GLUtils.deleteTextures(intArrayOf(originalTextureId))
      originalTextureId = 0
    }
    if (faceUnity2DTexId != 0) {
      GLUtils.deleteTextures(intArrayOf(faceUnity2DTexId))
      faceUnity2DTexId = 0
    }
    glProgram?.let {
      it.release()
      glProgram = null
    }
    glRenderListener?.onSurfaceDestroy?.invoke()
  }


  @RequiresApi(Build.VERSION_CODES.O)
  override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    glProgram = GLProgram()
    frameCount = 0
    onSurfaceCreate = true
    originalTextureId = GLUtils.createTextures(GLES11Ext.GL_TEXTURE_EXTERNAL_OES)
    fuRenderInputData.texture?.textureId = originalTextureId

    // TODO: 2023-10-26 17:38:00 少了个 ProgramTextureOES (VideoRender)

    surfaceTexture = SurfaceTexture(originalTextureId)
    surface = Surface(surfaceTexture)

    androidMediaPlayer = AndroidMediaPlayer(glSurfaceView.context)
      .initPlayer()
      .setSurface(surface)
      .setDataSource(videoPath)
      .prepareAsync()
      .setPlayerListener {
        onError = { type, error ->
          outPlayerListener?.onError?.let { it(type, error) }
          playPosition = 0
        }
        onCompletion = {
          outPlayerListener?.onCompletion?.invoke()
          playPosition = androidMediaPlayer.duration.toLong()
          isCompleted = true
        }
        onPrepared = {
          androidMediaPlayer.start()
          if (playPosition > 0 && !isCompleted) {
            androidMediaPlayer.seekTo(playPosition, MediaPlayer.SEEK_CLOSEST)
          }
          androidMediaPlayer.pause()
          outPlayerListener?.onPrepared?.invoke()
        }
      }

    glRenderListener?.onSurfaceCreated?.invoke()
  }

  /**
   * 啥玩意？矩阵变换
   */
  override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    GLES20.glViewport(0, 0, width, height)
    if (glSurfaceViewWidth != width || glSurfaceViewHeight != height) {
      glSurfaceViewWidth = width
      glSurfaceViewHeight = height

      originMvpMatrix = if (videoOrientation == 0 || videoOrientation == 180) {
        GLUtils.changeMvpMatrixInside(
          width.toFloat(),
          height.toFloat(),
          originalWidth.toFloat(),
          originalHeight.toFloat()
        )
      } else {
        GLUtils.changeMvpMatrixInside(
          width.toFloat(),
          height.toFloat(),
          originalHeight.toFloat(),
          originalWidth.toFloat()
        )
      }
      smallViewMatrix =
        GLUtils.changeMvpMatrixCrop(90f, 160f, originalHeight.toFloat(), originalWidth.toFloat())
      defaultFUMvpMatrix = originMvpMatrix.copyOf()
      when (videoOrientation) {
        270 -> {
          Matrix.rotateM(defaultFUMvpMatrix, 0, 90f, 0f, 0f, 1f)
        }

        180 -> {
          Matrix.rotateM(defaultFUMvpMatrix, 0, 180f, 0f, 0f, 1f)
        }

        90 -> {
          Matrix.rotateM(defaultFUMvpMatrix, 0, 270f, 0f, 0f, 1f)
        }
      }
    }
    smallViewportX = width - smallViewportWidth - smallViewportHorizontalPadding
    smallViewportY = smallViewportBottomPadding
    glRenderListener?.onSurfaceChanged?.invoke(width, height)

  }

  /**
   * 啥玩意
   */
  override fun onDrawFrame(gl: GL10?) {
    if (isActivityPause) return
    if (surfaceTexture == null || glProgram == null) return
    surfaceTexture?.updateTexImage()
    surfaceTexture?.getTransformMatrix(originMvpMatrix)

    if (fuRenderInputData.imageBuffer == null || fuRenderInputData.imageBuffer?.buffer == null
      && (fuRenderInputData.texture == null || fuRenderInputData.texture?.textureId!! <= 0)
    ) return
    /* 特效合成，并通过回调确认最终渲染数据，合成数据，渲染矩阵  */
    if (renderSwitch && frameCount++ >= frameFuRenderMinCount) {
      var frameData: FURenderFrameData
      when (videoOrientation) {
        0 -> {
          //对video特殊矩阵进行偏移设置
          val matrix = defaultFUTexMatrix.copyOf()
          Matrix.scaleM(matrix, 0, 1.0f, -originTexMatrix[5], 1.0f)
          Matrix.translateM(matrix, 0, 0.0f, 1 + originTexMatrix[5], 0.0f)
          FURenderFrameData(matrix, defaultFUMvpMatrix.copyOf()).also { frameData = it }
        }
      }
      frameData = FURenderFrameData(defaultFUTexMatrix.copyOf(), defaultFUMvpMatrix.copyOf())

      if (frameInterval != 0L) {
        if (renderMode == RenderModeEnum.EXPORT || renderMode == RenderModeEnum.NORMAL) {
          if (videoHash.isNotEmpty()) {
            mFURenderKit.setVideoHash(
              if (renderMode == RenderModeEnum.NORMAL) 0 else 1,
              videoHash,
              getFrameIndex()
            )
          }
        }
      }
      glRenderListener?.onRenderBefore?.invoke(fuRenderInputData)//特效合成前置处理
      currentFURenderOutputData = mFURenderKit.renderWithInput(fuRenderInputData)//特效合成
      faceUnity2DTexId = currentFURenderOutputData!!.texture.textureId
      glRenderListener?.onRenderAfter?.invoke(currentFURenderOutputData!!, frameData)//纹理合成后置处理
      currentFUTexMatrix = frameData.texMatrix
      currentFUMvpMatrix = frameData.mvpMatrix
    }

    //渲染
    drawRenderFrame(gl)
    //渲染完成回调
    glRenderListener?.onDrawFrameAfter?.invoke()
    //循环调用
    if (isLimiterFPS) {
      LimitFpsUtil.limitFrameRate()//循环调用
      glSurfaceView.requestRender()
    }
  }

  //获取视频得基本信息
  private fun analysisVideo() {
    val mediaMetadataRetriever = MediaMetadataRetriever()
    try {
      mediaMetadataRetriever.setDataSource(videoPath)
      originalWidth =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toInt()
      originalHeight =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toInt()
      videoOrientation =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION).toInt()
      mDuration =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
      mFrameCount =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT).toInt()
      fuRenderInputData.apply {
        width = originalWidth
        height = originalHeight
        renderConfig.inputOrientation = videoOrientation
      }
      val frameRate =
        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)
      var calFrameRate = 0
      if (frameRate.isNullOrEmpty()) {
        calFrameRate = (mFrameCount * 1000 / mDuration).toInt()
      }
      l.d(
        "分析视频",
        "$originalWidth -- $originalHeight -- $mDuration -- $mFrameCount -- $frameRate -- $calFrameRate"
      )

      //横向视频去掉水平margins
      glSurfaceView.let {
        val isHorizontal: Boolean = if (videoOrientation == 0 || videoOrientation == 180) {
          originalWidth >= originalHeight
        } else {
          originalWidth < originalHeight && videoOrientation % 90 == 0
        }
        if (isHorizontal) {
          val parentView = it.parent
          if (parentView is View) {
            ViewUtils.setViewHorizontalMargins(parentView, 0, 0)
          }
          val screenWidth = ScreenUtils.getScreenWidth()
          ViewUtils.setViewSize(it, screenWidth, (originalHeight * 1.0 / originalWidth * screenWidth).toInt())
        }
      }
      if (FURenderConfig.isOpenSetVideoHash) {
        frameInterval = mDuration / (mFrameCount - 1)
        frameIndexBeanList.clear()
        mExtractFrameWorkThread =
          ExtractFrameWorkThread(videoPath, 0, mDuration, mFrameCount, frameIndexBeanList)
        mExtractFrameWorkThread?.start()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    } finally {
      mediaMetadataRetriever.release()
    }
  }


  private val DEFAULT_FRAME_COUNT = 30
  private var faces: ArrayList<Int>? = null//人数情况
  private var ages: ArrayList<Int>? = null //最大人脸年龄情况
  private var genders: ArrayList<Int>? = null //最大人脸性别情况
  private var skinColors: ArrayList<Int>? = null//最大人脸肤色情况
  private var analysisFrames = DEFAULT_FRAME_COUNT //分析30帧

  private fun drawRenderFrame(gl: GL10?) {
    if (renderMode == RenderModeEnum.ANALYSIS) {
      faces?.add(FUAIKit.getInstance().maxFaces)
      ages?.add(mFURenderKit.videoBeauty!!.getAutoAge())
      genders?.add(mFURenderKit.videoBeauty!!.getAutoAge())
      skinColors?.add(mFURenderKit.videoBeauty!!.getAutoAge())
      if (analysisFrames > 0) {
        analysisFrames--
      } else {
        renderMode = RenderModeEnum.NORMAL
        analysisFinish(smartBeautyType())
      }
    }
    if (renderMode == RenderModeEnum.EXPORT || (renderMode == RenderModeEnum.ANALYSIS && !isPlayNow())) {
      return
    }
    //正常模式
    if (faceUnity2DTexId > 0 && renderSwitch) {
      drawFrame(faceUnity2DTexId, currentFUTexMatrix, currentFUMvpMatrix)
      l.v("watchdog", "faceunitv2dtextid:$faceUnity2DTexId")
    }
  }

  private fun drawFrame(textureId: Int, textureMatrix: FloatArray, mvpMatrix: FloatArray) {
    //清空背景
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

    GLUtils.checkGLError("draw start")

    // Select the program.
    GLES20.glUseProgram(glProgram?.creteProgramId!!)
    GLUtils.checkGLError("glUseProgram")

    // Set the texture.
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)

    // Copy the model / view / projection matrix over.
    GLES20.glUniformMatrix4fv(glProgram?.uMVPMatrixLoc!!, 1, false, mvpMatrix, 0)
    GLUtils.checkGLError("glUniformMatrix4fv")

    // Copy the texture transformation matrix over.
    GLES20.glUniformMatrix4fv(glProgram?.uTexMatrixLoc!!, 1, false, textureMatrix, 0)
    GLUtils.checkGLError("glUniformMatrix4fv")

    // Enable the "aPosition" vertex attribute.
    GLES20.glEnableVertexAttribArray(glProgram?.aPositionLoc!!)
    GLUtils.checkGLError("glEnableVertexAttribArray")

    // Connect vertexBuffer to "aPosition".
    GLES20.glVertexAttribPointer(
      glProgram?.aPositionLoc!!, COORDINATE_PER_VERTEX,
      GLES20.GL_FLOAT, false, VERTEX_STRIDE, glProgram?.vertexFloatBuffer
    )
    GLUtils.checkGLError("glVertexAttribPointer")

    // Enable the "aTextureCoord" vertex attribute.
    GLES20.glEnableVertexAttribArray(glProgram?.aTextureCoordinateLoc!!)
    GLUtils.checkGLError("glEnableVertexAttribArray")

    // Connect texBuffer to "aTextureCoord".
    GLES20.glVertexAttribPointer(
      glProgram?.aTextureCoordinateLoc!!, 2,
      GLES20.GL_FLOAT, false, TEXTURE_COORDINATE_STRIDE, glProgram?.texFloatBuffer
    )
    GLUtils.checkGLError("glVertexAttribPointer")

    // Draw the rect.
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, glProgram?.vertexCount!!)
    GLUtils.checkGLError("glDrawArrays")

    // Done -- disable vertex array, texture, and program.
    GLES20.glDisableVertexAttribArray(glProgram?.aPositionLoc!!)
    GLES20.glDisableVertexAttribArray(glProgram?.aTextureCoordinateLoc!!)
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    GLES20.glUseProgram(0)
  }


  private fun smartBeautyType(): String {
    var hasFace: Boolean = false
    faces?.forEach {
      if (it > 0) hasFace = true
    }
    //如果没有人脸直接返回应该展示经典模式
    if (!hasFace) return VideoSmartFaceBeautyEnum.CONFIG_CLASSIC

    //其他不同条件返回不同的模式
    return VideoSmartFaceBeautyEnum.CONFIG_CLASSIC
  }


  private fun getFrameIndex(): Int {
    val playMils = if (isCompleted) mDuration else androidMediaPlayer.currentPosition
    var frameIndex = 0
    if (frameIndexBeanList.size > 0) {
      if (playMils <= frameIndexBeanList[0].frameTimeMs) {
        //缓存前面
        frameIndex = frameIndexBeanList[0].frameIndex
      } else if (isCompleted || playMils >= frameIndexBeanList[frameIndexBeanList.size - 1].frameTimeMs) {
        //缓存后面
        frameIndex = frameIndexBeanList[frameIndexBeanList.size - 1].frameIndex
      } else {
        //缓存范围内
        for (i in frameIndexBeanList.indices) {
          //当前位置
          if (playMils == frameIndexBeanList[i].frameTimeMs) {
            frameIndex = frameIndexBeanList[i].frameIndex
            break
          }
          //下一个位置
          if (playMils == frameIndexBeanList[i + 1].frameTimeMs) {
            frameIndex = frameIndexBeanList[i + 1].frameIndex
            break
          }
          //处于中间，取左边
          if (playMils > frameIndexBeanList[i].frameTimeMs && playMils < frameIndexBeanList[i + 1].frameTimeMs) {
            frameIndex = frameIndexBeanList[i].frameIndex
            break
          }
        }
      }
    }
    return frameIndex
  }


  fun setPlayerListener(listener: PlayerListener.() -> Unit) {
    outPlayerListener = PlayerListener().also(listener)
  }

  fun setGLRenderListener(listener: GLRenderListener.() -> Unit) {
    glRenderListener = GLRenderListener().also(listener)
  }

  class GLRenderListener {
    var onSurfaceCreated: () -> Unit = {}
    var onSurfaceChanged: (width: Int, height: Int) -> Unit = { _, _ -> }
    var onRenderBefore: (inputData: FURenderInputData) -> Unit = {}
    var onRenderAfter: (outputData: FURenderOutputData, frameData: FURenderFrameData) -> Unit = { _, _ -> }
    var onDrawFrameAfter: () -> Unit = {}
    var onSurfaceDestroy: () -> Unit = {}
  }

  private var analysisFinish: (smartBeautyType: String) -> Unit = {}

  fun analysisFinish(analysisFinish: (smartBeautyType: String) -> Unit) {
    this.analysisFinish = analysisFinish
  }


}