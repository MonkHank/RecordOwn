package com.monk.commonutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * @author JackieHank
 * @date 2017-08-11 17:51.
 */

public class DeviceUtils {
    private final static String TAG = "DeviceUtils";

    public static String getScreenHeightAndWidth(Activity activity) {
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        return "height：" + height + "\twidth：" + width;
    }

    /*** 获取屏幕宽高多少（像素）*/
    public static int[] getWidthAndHeight(Context context){
        int[] ints = new int[2];
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        ints[0]=widthPixels;
        ints[1]=heightPixels;
        return ints;
    }

    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static float getDensity(Context context) {
        return getWidthAndHeight(context)[0]/360f;
//        return context.getResources().getDisplayMetrics().density;
    }

    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static String getDensityAndDensityDpi(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return "density:" + density + "\tdensityDpi(dpi):" + densityDpi;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        float dip = px / density;
        return (int) dip;
    }

    public static long msToMin(long ms) {
        long consumedSecond;
        //取余数
        long s = ms / 1000 % 60;
        long m = ms / 1000 / 60 % 60;
        long h = ms / 1000 / 60 / 60;
        if (h < 1) {
            consumedSecond = m * 60 + s;
        } else {
            consumedSecond = h * 3600 + s;
        }
        long currentMin = consumedSecond / 60;
        return currentMin;
    }

    public static String[] getDeviceNoAndModel(Context context) {
        String[] str = new String[2];
        // 针对华为mate10下面这个反射方式是获取不到的；
        /*try {
            Method get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            String boot = (String) get.invoke(null, "ro.boot.serialno");
            if (TextUtils.isEmpty(boot)) {
                boot=(String) get.invoke(null, "ro.serialno");
            }
            str[0]=boot;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        str[0]=Build.SERIAL;
//        换成阿里给的
//        str[0] = PrefUtils.getAliDeviceId(context);
        str[1] = Build.BRAND + "-" + Build.DEVICE;
        return str;
    }

    public static String getModel() {
        return "SDK:"+ Build.VERSION.SDK_INT+"\n"
                +"MODEL:" + Build.MODEL + "\n"
                +"BOARD:" + Build.BOARD + "\n"
                +"BRAND:" + Build.BRAND + "\n"
                +"DEVICE:" + Build.DEVICE + "\n"
                +"DISPLAY:" + Build.DISPLAY + "\n"
                +"FINGERPRINT:" + Build.FINGERPRINT + "\n"
                +"HARDWARE:" + Build.HARDWARE + "\n"
                +"MANUFACTURER:" + Build.MANUFACTURER + "\n"
                +"SDK_INT:" + Build.VERSION.SDK_INT + "\n"
                +"ID:" + Build.ID + "\n"
                +"SERIAL:" + Build.SERIAL + "\n";
    }

    /**
     * 通过反编译来获取设备信息
     * @return String
     */
    public static String decompile() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " : " + field.get(null) + "\n");
            } catch (Exception e) {
                LogUtil.i(TAG, "an error occurred when collect crash info");
            }
        }
        return sb.toString();
    }

    public static String getMaxMemoryInfo(Context context){
        StringBuilder sb = new StringBuilder();
        Runtime rt= Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
        ActivityManager ac = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = ac.getMemoryClass();
        int largeMemoryClass = ac.getLargeMemoryClass();
        sb.append("rt.maxMemory:" + (maxMemory/1024/1024)+"\t");
        sb.append("ac.memoryClass:" + memoryClass+"\t");
        sb.append("ac.largeMemoryClass:" + largeMemoryClass+"\t");
        return sb.toString();
    }

    // 获取CPU最大频率（单位KHZ）
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    private static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 获取CPU最小频率（单位KHZ）
     * @return String
     */
    private static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     * @return String
     */
    private static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 获取CPU名字
    private static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
