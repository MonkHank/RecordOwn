package com.monk.commonutils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.TypedValue
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException

/**
 * @author JackieHank
 * @date 2017-08-11 17:51.
 */
object DeviceUtils {
    private const val TAG = "DeviceUtils"

    fun getScreenHeightAndWidth(activity: Activity): String {
        val height = activity.windowManager.defaultDisplay.height
        val width = activity.windowManager.defaultDisplay.width
        return "height：$height\twidth：$width"
    }

    /*** 获取屏幕宽高多少（像素） */
    fun getWidthAndHeight(context: Context): IntArray {
        val ints = IntArray(2)
        val res = context.resources
        val dm = res.displayMetrics
        val widthPixels = dm.widthPixels
        val heightPixels = dm.heightPixels
        ints[0] = widthPixels
        ints[1] = heightPixels
        return ints
    }

    fun getScreenHeight(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.height
    }

    fun getDensity(context: Context): Float {
        return getWidthAndHeight(context)[0] / 360f
        //        return context.getResources().getDisplayMetrics().density;
    }

    fun getDensityDpi(context: Context): Int {
        return context.resources.displayMetrics.densityDpi
    }

    fun getDensityAndDensityDpi(context: Context): String {
        val density = context.resources.displayMetrics.density
        val densityDpi = context.resources.displayMetrics.densityDpi
        return "density:$density\tdensityDpi(dpi):$densityDpi"
    }

    fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                context.resources.displayMetrics).toInt()
    }

    fun px2dip(context: Context, px: Int): Int {
        val density = context.resources.displayMetrics.density
        val dip = px / density
        return dip.toInt()
    }

    fun msToMin(ms: Long): Long {
        val consumedSecond: Long
        //取余数
        val s = ms / 1000 % 60
        val m = ms / 1000 / 60 % 60
        val h = ms / 1000 / 60 / 60
        consumedSecond = if (h < 1) {
            m * 60 + s
        } else {
            h * 3600 + s
        }
        return consumedSecond / 60
    }

    fun getDeviceNoAndModel(context: Context?): Array<String?> {
        val str = arrayOfNulls<String>(2)
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
        }*/str[0] = Build.SERIAL
        //        换成阿里给的
//        str[0] = PrefUtils.getAliDeviceId(context);
        str[1] = Build.BRAND + "-" + Build.DEVICE
        return str
    }

    val model: String
        get() = """
               SDK:${Build.VERSION.SDK_INT}
               MODEL:${Build.MODEL}
               BOARD:${Build.BOARD}
               BRAND:${Build.BRAND}
               DEVICE:${Build.DEVICE}
               DISPLAY:${Build.DISPLAY}
               FINGERPRINT:${Build.FINGERPRINT}
               HARDWARE:${Build.HARDWARE}
               MANUFACTURER:${Build.MANUFACTURER}
               SDK_INT:${Build.VERSION.SDK_INT}
               ID:${Build.ID}
               SERIAL:${Build.SERIAL}
               
               """.trimIndent()

    /**
     * 通过反编译来获取设备信息
     * @return String
     */
    fun decompile(): String {
        val sb = StringBuilder()
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                sb.append("""${field.name} : ${field[null]}
""")
            } catch (e: Exception) {
                LogUtil.i(TAG, "an error occurred when collect crash info")
            }
        }
        return sb.toString()
    }

    fun getMaxMemoryInfo(context: Context): String {
        val sb = StringBuilder()
        val rt = Runtime.getRuntime()
        val maxMemory = rt.maxMemory()
        val ac = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryClass = ac.memoryClass
        val largeMemoryClass = ac.largeMemoryClass
        sb.append("rt.maxMemory:" + maxMemory / 1024 / 1024 + "\t")
        sb.append("ac.memoryClass:$memoryClass\t")
        sb.append("ac.largeMemoryClass:$largeMemoryClass\t")
        return sb.toString()
    }

    // 获取CPU最大频率（单位KHZ）
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    private val maxCpuFreq: String
        private get() {
            var result = ""
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat",
                        "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                result = "N/A"
            }
            return result.trim { it <= ' ' }
        }

    /**
     * 获取CPU最小频率（单位KHZ）
     * @return String
     */
    private val minCpuFreq: String
        private get() {
            var result = ""
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat",
                        "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                result = "N/A"
            }
            return result.trim { it <= ' ' }
        }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     * @return String
     */
    private val curCpuFreq: String
        private get() {
            var result = "N/A"
            try {
                val fr = FileReader(
                        "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")
                val br = BufferedReader(fr)
                val text = br.readLine()
                result = text.trim { it <= ' ' }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return result
        }

    // 获取CPU名字
    private val cpuName: String?
        private get() {
            try {
                val fr = FileReader("/proc/cpuinfo")
                val br = BufferedReader(fr)
                val text = br.readLine()
                val array: List<String> = text.split(":\\s+")
                return array[1]
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
}

