package com.monk.moduleviews.eventdispatch

import android.animation.TimeInterpolator
import kotlin.math.sin

/**
 * @author monk
 * @date 2019-06-05
 */
class SinInter : TimeInterpolator {
    /**
     * Sin
     * 1. 先快后慢
     * 2. 先慢后快
     *
     *
     * @param input 是一个从0~1均匀变化的值
     * @return
     */
    override fun getInterpolation(input: Float): Float {
        //从0到PI/2均匀变化的值
        val rad = (Math.PI / 2 * input).toFloat()

        //返回这个弧度的sin值--sin曲线在0~PI/2区域是增长越来越缓慢，小球运动越来越缓慢
        return sin(rad.toDouble()).toFloat()

//        //返回这个弧度的sin值--sin曲线在PI/2~PI区域是降低越来越快
//        return (float) (1-(Math.sin(rad)));//返回1-
    }
}