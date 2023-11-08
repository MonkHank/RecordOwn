package com.monk.modulegl

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.viewbinding.binding
import com.monk.activity.base.BaseCompatActivity
import com.monk.commonutils.l
import com.monk.modulegl.databinding.ActMainBinding

/**
 * @author monk
 * @since 2023-10-26 10:20:52
 */
@Route(path = "/module_gl/ActMain")
class ActMain : BaseCompatActivity<ActMain>() {

  private val mBinding: ActMainBinding by binding(false)

  private var videoWidth: Int = 0
  private var videoHeight: Int = 0

  private var srcPath = "/storage/emulated/0/video/20231020-135524.mp4"

  @Volatile
  private var isSendRecordingData = false

  private lateinit var glRender: GLRender

  private var videoBeautyDataFactory: VideoBeautyDataFactory? = null
  private var oneLevelActionCacheKit: FUTwoLevelVideoCacheKit<String>? = null //1级 滤镜/美颜
  private var twoLevelActionCacheKit: FUTwoLevelVideoCacheKit<Double>? = null //2级 美肤/美型/一键美化
  private var mFuVideoBeautyData: FUVideoBeautyData? = null

  private var onCreate = true

  @RequiresApi(Build.VERSION_CODES.P)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    simpleName = "GL"
    initToolbar(R.layout.act_main)

    mFURenderKit.setVideoProcessMode(false)
    videoBeautyDataFactory = VideoBeautyDataFactory()
    oneLevelActionCacheKit = FUTwoLevelVideoCacheKit()
    twoLevelActionCacheKit = FUTwoLevelVideoCacheKit()

    glRender = GLRender(mBinding.glSurface, srcPath)
    glRender.setGLRenderListener {
      onRenderAfter = { outputData, frameData ->
        videoWidth = outputData.texture.width
        videoHeight = outputData.texture.height
      }
      onSurfaceCreated = {
        mFUAIKit.loadAIProcessor(DemoConfig.BUNDLE_AI_FACE_PROCESSOR, FUAITypeEnum.FUAITYPE_FACEPROCESSOR)
        mFUAIKit.loadAIProcessor(DemoConfig.BUNDLE_AI_FACE_BEAUTY_VIDEO_PROCESSOR, FUAITypeEnum.FUAITYPE_VIDEOPROCESSOR)
        videoBeautyDataFactory.bindCurrentRenderer()
        runOnUiThread { videoBeautyDataFactory.setEnabled(true) }
        if (onCreate) {
          onCreate = false
          mFuVideoBeautyData = VideoBeautyUtils.buildVideoBuildData(mFURenderKit.getVideoBeauty())
        }
      }
      onSurfaceDestroy = { mFURenderKit.release() }
    }
    glRender.setPlayerListener {
      onCompletion = { l.i("视频播放完毕") }
    }
  }

  override fun onResume() {
    super.onResume()
    glRender.onResume()
  }

  override fun onPause() {
    super.onPause()
    glRender.onPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    glRender.onDestroy()
  }
}