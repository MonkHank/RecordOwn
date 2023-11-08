package com.monk.z_copy

import android.os.Bundle
import com.dylanc.viewbinding.binding
import com.monk.activity.base.BaseCompatActivity
import com.monk.z_copy.databinding.ActMainBinding

/**
 * @author monk
 * @since 2023/10/25 15:59
 */
class ActMain : BaseCompatActivity<ActMain>() {

  private val mBinding: ActMainBinding by binding(false)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar(R.layout.act_main)
  }
}