package com.monk.ktx

import android.app.Activity
import android.view.View

/**
 * @author monk
 * @date 2021/11/25下午 10:25
 */


fun <T:View>Activity.id(redId:Int)=lazy{
    findViewById<T>(redId)
}
