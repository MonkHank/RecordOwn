package com.monk.commonutils;

import android.app.Activity;
import android.content.Context;

public class DeviceHelper {
    private DeviceHelper(){}

    private static class SingleTonHoler{
        private static DeviceHelper INSTANCE = new DeviceHelper();
    }

    public static DeviceHelper getInstance(){
        return SingleTonHoler.INSTANCE;
    }

    public String getDeviceMsg(Context context) {

        StringBuilder sb = new StringBuilder();
        sb.append("设备信息：" + DeviceUtils.decompile()).append("\n")
                .append("设备信息："+DeviceUtils.getModel()).append("\n")
                .append("内存占用信息："+DeviceUtils.getMaxMemoryInfo(context)).append("\n")
                .append("设备屏幕信息："+DeviceUtils.getScreenHeightAndWidth((Activity) context)).append("\n")
                .append("设备屏幕密度："+DeviceUtils.getDensityAndDensityDpi(context)).append("\n")
                .append("");
        return sb.toString();
    }
}