package com.monk.eventdispatch;

import android.animation.TypeEvaluator;

/**
 * @author monk
 * @date 2019-06-05
 */
public class BallEvaluator implements TypeEvaluator<Ball> {

    /**
     * @param fraction   The fraction from the starting to the ending values
     * @param startValue 小球初始状态
     * @param endValue   小球终止状态
     * @return
     */
    @Override
    public Ball evaluate(float fraction, Ball startValue, Ball endValue) {

        // 当前小球
        Ball ball = new Ball();
        //半径=初始+分率*(结尾-初始) 比如运动到一半，分率是0.5
        ball.r = (int) (startValue.r + fraction * (endValue.r - startValue.r));
        //颜色怎么渐变?
        ball.color = evaluateColor(fraction, startValue.color, endValue.color);

        return ball;
    }

    /**
     * 根据分率计算颜色 ArgbEvaluator 内部的算法，直接拷贝
     */
    private int evaluateColor(float fraction, int startInt, int endInt) {
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;

        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
