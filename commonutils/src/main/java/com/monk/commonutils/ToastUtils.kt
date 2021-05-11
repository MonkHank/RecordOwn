package com.monk.commonutils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

/**
 * @author Administrator
 * @date 2017/8/2/002.
 */
object ToastUtils {
    private var mToast: Toast? = null
    private var mToast2: Toast? = null
    private var mTvToast: TextView? = null
    fun showToast(context: Context?, text: String?) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
        }
        mToast!!.show()
    }

    fun showImageToast(context: Context, text: String) {
        if (mToast2 == null) {
            mToast2 = Toast(context)
            mToast2!!.duration = Toast.LENGTH_SHORT
            mToast2!!.setGravity(Gravity.CENTER, 0, 0)
            val view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null)
            mTvToast = view.findViewById(R.id.tv_save_contact)
            mToast2!!.view = view
        }
        mTvToast!!.text = text
        mToast2!!.show()
    }

    fun destroyExToast() {
        if (mToast2 != null) {
            mToast2 = null
            mTvToast = null
        }
    }

    fun destroyToast() {
        if (mToast != null) {
            mToast = null
        }
    }
}