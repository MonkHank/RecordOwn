package com.monk.commonutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {

    public static String getMD5(String s) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md5 != null;
        md5.update(s.getBytes());
        // 加密
        byte[] m = md5.digest();
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (byte aB : b) {
            int a = aB;
            if (a < 0) {
                a += 256;
            }
            if (a < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(a));

        }
        // 32位
        //		return buf.toString();
        //16位
        return buf.toString().substring(8,24);
    }
}
