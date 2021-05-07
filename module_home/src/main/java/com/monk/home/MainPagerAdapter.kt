package com.monk.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class MainPagerAdapter @SuppressLint("WrongConstant") constructor(private val context: Context,
                                                                  fm: FragmentManager,
                                                                  private val list: List<Fragment>)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabsTextList: List<String> = listOf(
            context.getString(R.string.working_station),
            context.getString(R.string.record),
            context.getString(R.string.mine))
    private val drawableUnselected = intArrayOf(R.mipmap.workingstation2, R.mipmap.record2, R.mipmap.mine2)
    private val drawableSelected = intArrayOf(R.mipmap.workingstation, R.mipmap.record, R.mipmap.mine)
    private val imageViews: Array<ImageView?> = arrayOfNulls(list.size)
    private val tvs: Array<TextView?> = arrayOfNulls(list.size)

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    /**
     * 初始化
     * @param position
     * @return
     */
    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main_tab, null)
        val ivLogo = view.findViewById<ImageView>(R.id.ivLogo)
        val tvText = view.findViewById<TextView>(R.id.tvText)
        ivLogo.setImageResource(drawableUnselected[position])
        tvText.text = tabsTextList[position]
        if (position == 0) {
            ivLogo.setImageResource(drawableSelected[0])
            tvText.setTextColor(context.resources.getColor(R.color.status_bar_bg))
        }
        imageViews[position] = ivLogo
        tvs[position] = tvText
        return view
    }

    fun onTabSelected(position: Int) {
        imageViews[position]!!.setImageResource(drawableSelected[position])
        tvs[position]!!.setTextColor(context.resources.getColor(R.color.status_bar_bg))
    }

    fun onTabUnSelected(position: Int) {
        imageViews[position]!!.setImageResource(drawableUnselected[position])
        tvs[position]!!.setTextColor(Color.argb(255, 51, 51, 51))
    }

}