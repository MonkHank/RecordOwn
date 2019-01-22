package com.monk.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author monk
 * @date 2019-01-21
 */
public class BitmapUtils {

    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,resId,options);

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize=1;
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;

        if (rawHeight > reqHeight || rawWidth > reqWidth) {
            int halfRawHeight = rawHeight / 2;
            int halfRawWidth = rawWidth / 2;
            while (halfRawHeight / inSampleSize >= reqHeight && halfRawWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
