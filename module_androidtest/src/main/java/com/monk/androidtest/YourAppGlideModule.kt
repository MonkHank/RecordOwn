package com.monk.androidtest

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class YourAppGlideModule : AppGlideModule() {

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    // 缓存okhttp请求
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
  }

}