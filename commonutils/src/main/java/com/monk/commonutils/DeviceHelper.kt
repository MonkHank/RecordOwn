package com.monk.commonutils

import android.app.Activity
import android.content.Context

/**
 * 静态内部类
 */
class DeviceHelper private constructor(){

    companion object{
        val instance=Holder.holder
    }
    private object Holder{
        val holder=DeviceHelper()
    }

    fun getDeviceMsg(context: Context?): String? {
        val sb = StringBuilder()
        sb.append("设备信息：" + DeviceUtils.decompile()).append("\n")
                .append("设备信息：" + DeviceUtils.getModel()).append("\n")
                .append("内存占用信息：" + DeviceUtils.getMaxMemoryInfo(context)).append("\n")
                .append("设备屏幕信息：" + DeviceUtils.getScreenHeightAndWidth(context as Activity?)).append("\n")
                .append("设备屏幕密度：" + DeviceUtils.getDensityAndDensityDpi(context)).append("\n")
                .append("")
        return sb.toString()
    }
}