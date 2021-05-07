package com.monk.home

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

abstract class OnTabLayoutTabSelectListener : OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        selectTab(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        unSelectTab(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {}
    abstract fun selectTab(tab: TabLayout.Tab?)
    abstract fun unSelectTab(tab: TabLayout.Tab?)
}