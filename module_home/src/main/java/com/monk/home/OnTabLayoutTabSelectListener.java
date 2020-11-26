package com.monk.home;

import com.google.android.material.tabs.TabLayout;

public abstract class OnTabLayoutTabSelectListener implements TabLayout.OnTabSelectedListener {

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectTab(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        unSelectTab(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public abstract void selectTab(TabLayout.Tab tab);
    public abstract void unSelectTab(TabLayout.Tab tab);
}