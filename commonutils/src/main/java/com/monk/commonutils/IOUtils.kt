package com.monk.commonutils

import java.io.Closeable
import java.io.IOException

/**
 * public class IOUtils {
    /** 关闭流 */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtil.e(e.getMessage());
            }
        }
        return true;
        }
    }
 */
object IOUtils {
    /** 关闭流  */
	@JvmStatic
	fun close(io: Closeable?): Boolean {
        if (io != null) {
            try {
                io.close()
            } catch (e: IOException) {
                LogUtil.e(e.message!!)
            }
        }
        return true
    }
}



