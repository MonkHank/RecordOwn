package com.monk.activity.base

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.monk.basic.R
import com.monk.global.IStatusView
import com.monk.ktx.id

/**
 * @author monk
 * @date 2021-11-25 22:18:57
 */
abstract class BaseMvvmActivity:AppCompatActivity(),IStatusView {

    private val mTvContent: TextView by id(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}