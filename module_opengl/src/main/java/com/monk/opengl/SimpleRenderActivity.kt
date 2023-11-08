package com.monk.opengl

import android.graphics.BitmapFactory
import android.os.Bundle
import com.dylanc.viewbinding.binding
import com.monk.activity.base.BaseCompatActivity
import com.monk.opengl.databinding.ActSimpleRenderBinding

/**
 * @author monk
 * @since 2023/10/25 15:59
 */
class SimpleRenderActivity : BaseCompatActivity<SimpleRenderActivity>() {

  private val mBinding: ActSimpleRenderBinding by binding()

  //自定义的OpenGL渲染器，详情请继续往下看
  private lateinit var drawer: IDrawer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar(R.layout.act_simple_render)

    drawer = if (intent.getIntExtra("type", 0) == 0) {
      TriangleDrawer()
    } else {
      BitmapDrawer(0,BitmapFactory.decodeResource(mContext!!.resources, R.drawable.amap_man))
    }
    initRender(drawer)
  }

  private fun initRender(drawer: IDrawer) {
    mBinding.glSurfaceView.setEGLContextClientVersion(2)
    mBinding.glSurfaceView.setRenderer(SimpleRender(drawer))
  }

  override fun onDestroy() {
    drawer.release()
    super.onDestroy()
  }
}