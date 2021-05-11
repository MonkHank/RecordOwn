package com.monk.commonutils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Utils {
    fun getMD5(s: String): String {
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        assert(md5 != null)
        md5!!.update(s.toByteArray())
        // 加密
        val m = md5.digest()
        return getString(m)
    }

    private fun getString(b: ByteArray): String {
        val buf = StringBuffer()
        for (aB in b) {
            var a = aB.toInt()
            if (a < 0) {
                a += 256
            }
            if (a < 16) {
                buf.append("0")
            }
            buf.append(Integer.toHexString(a))
        }
        // 32位
        //		return buf.toString();
        //16位
        return buf.toString().substring(8, 24)
    }
}