package com.monk.eventdispatch;

import android.animation.TimeInterpolator;

/**
 * @author monk
 * @date 2019-06-05
 */
public class SinInter implements TimeInterpolator {

    /**
     * Sin
     * 1. 先快后慢
     * 2. 先慢后快
     *
     *
     * @param input 是一个从0~1均匀变化的值
     * @return
     */
    @Override
    public float getInterpolation(float input) {
        //从0到PI/2均匀变化的值
        float rad = (float) (Math.PI / 2 * input);

        //返回这个弧度的sin值--sin曲线在0~PI/2区域是增长越来越缓慢，小球运动越来越缓慢
        return (float) (Math.sin(rad));

//        //返回这个弧度的sin值--sin曲线在PI/2~PI区域是降低越来越快
//        return (float) (1-(Math.sin(rad)));//返回1-
    }
}
