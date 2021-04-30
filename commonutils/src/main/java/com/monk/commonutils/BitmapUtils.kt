package com.monk.commonutils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * @author monk
 * @date 2019-01-21
 */
class BitmapUtils {
    fun decodeSampleBitmapFromResource(res: Resources?, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        var inSampleSize = 1
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight
        if (rawHeight > reqHeight || rawWidth > reqWidth) {
            val halfRawHeight = rawHeight / 2
            val halfRawWidth = rawWidth / 2
            while (halfRawHeight / inSampleSize >= reqHeight && halfRawWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}