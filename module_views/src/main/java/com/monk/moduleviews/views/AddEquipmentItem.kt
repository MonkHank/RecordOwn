package com.monk.moduleviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.monk.moduleviews.R

/**
 * @author monk
 * @date 2018/11/6
 */
class AddEquipmentItem(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    val tvContent: TextView
    private val mLinearLayout: LinearLayout
    val etContent: EditText

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.views_page_publish_task, this, true)
        mLinearLayout = view.findViewById(R.id.linearLayout)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        tvContent = view.findViewById(R.id.tvContent)
        etContent = view.findViewById(R.id.etContent)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AddEquipmentItem)
        if (ta.getText(R.styleable.AddEquipmentItem_tvName) != null) {
            tvName.text = ta.getText(R.styleable.AddEquipmentItem_tvName)
        }
        if (ta.getText(R.styleable.AddEquipmentItem_tvContent) != null) {
            tvContent.text = ta.getText(R.styleable.AddEquipmentItem_tvContent)
        }
        if (ta.getText(R.styleable.AddEquipmentItem_etContentHint) != null) {
            etContent.hint = ta.getText(R.styleable.AddEquipmentItem_etContentHint)
        }
        val visibility = ta.getInteger(R.styleable.AddEquipmentItem_linearLayoutVisibility, 0)
        val tvVisibility = ta.getInteger(R.styleable.AddEquipmentItem_tvVisibility, 0)
        val etVisibility = ta.getInteger(R.styleable.AddEquipmentItem_etVisibility, 8)
        widgetVisibility(mLinearLayout, visibility)
        widgetVisibility(tvContent, tvVisibility)
        widgetVisibility(etContent, etVisibility)
        ta.recycle()
    }

    private fun widgetVisibility(view: View, visibility: Int) {
        when (visibility) {
            VISIBLE -> view.visibility = VISIBLE
            GONE -> view.visibility = GONE
            INVISIBLE -> view.visibility = INVISIBLE
            else -> {
            }
        }
    }

    fun setLinearVisibility(visibility: Int) {
        mLinearLayout.visibility = visibility
    }
}