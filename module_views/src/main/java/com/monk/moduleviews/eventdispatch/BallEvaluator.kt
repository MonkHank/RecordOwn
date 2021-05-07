package com.monk.moduleviews.eventdispatch

import android.animation.TypeEvaluator

/**
 * @author monk
 * @date 2019-06-05
 */
class BallEvaluator : TypeEvaluator<Ball> {
    /**
     * @param fraction   The fraction from the starting to the ending values
     * @param startValue 小球初始状态
     * @param endValue   小球终止状态
     * @return
     */
    override fun evaluate(fraction: Float, startValue: Ball, endValue: Ball): Ball {

        // 当前小球
        val ball = Ball()
        //半径=初始+分率*(结尾-初始) 比如运动到一半，分率是0.5
        ball.r = (startValue.r + fraction * (endValue.r - startValue.r)).toInt()
        //颜色怎么渐变?
        ball.color = evaluateColor(fraction, startValue.color, endValue.color)
        return ball
    }

    /**
     * 根据分率计算颜色 ArgbEvaluator 内部的算法，直接拷贝
     */
    private fun evaluateColor(fraction: Float, startInt: Int, endInt: Int): Int {
        val startA = (startInt shr 24 and 0xff) / 255.0f
        var startR = (startInt shr 16 and 0xff) / 255.0f
        var startG = (startInt shr 8 and 0xff) / 255.0f
        var startB = (startInt and 0xff) / 255.0f
        val endA = (endInt shr 24 and 0xff) / 255.0f
        var endR = (endInt shr 16 and 0xff) / 255.0f
        var endG = (endInt shr 8 and 0xff) / 255.0f
        var endB = (endInt and 0xff) / 255.0f

        // convert from sRGB to linear
        startR = Math.pow(startR.toDouble(), 2.2).toFloat()
        startG = Math.pow(startG.toDouble(), 2.2).toFloat()
        startB = Math.pow(startB.toDouble(), 2.2).toFloat()
        endR = Math.pow(endR.toDouble(), 2.2).toFloat()
        endG = Math.pow(endG.toDouble(), 2.2).toFloat()
        endB = Math.pow(endB.toDouble(), 2.2).toFloat()

        // compute the interpolated color in linear space
        var a = startA + fraction * (endA - startA)
        var r = startR + fraction * (endR - startR)
        var g = startG + fraction * (endG - startG)
        var b = startB + fraction * (endB - startB)

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f
        r = Math.pow(r.toDouble(), 1.0 / 2.2).toFloat() * 255.0f
        g = Math.pow(g.toDouble(), 1.0 / 2.2).toFloat() * 255.0f
        b = Math.pow(b.toDouble(), 1.0 / 2.2).toFloat() * 255.0f
        return Math.round(a) shl 24 or (Math.round(r) shl 16) or (Math.round(g) shl 8) or Math.round(b)
    }
}