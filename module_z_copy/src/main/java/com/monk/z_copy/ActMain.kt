package com.monk.z_copy

import android.os.Bundle
import com.monk.activity.base.BaseCompatActivity
import com.monk.module_z_copy.R

/**
 * @author monk
 * @since 2023/10/25 15:59
 */
class ActMain : BaseCompatActivity<ActMain>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar(R.layout.act_main)
  }
}