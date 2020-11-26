package com.monk.home;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.ui.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseCompatActivity<HomeActivity> {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    private MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_home);

        Fragment fragmentHome = FragmentHome.newFragment();
        Fragment fragmentDevelop = FragmentDevelop.newFragment();
        Fragment fragmentMine = FragmentMine.newFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentDevelop);
        fragmentList.add(fragmentMine);

        pagerAdapter = new MainPagerAdapter(mContext,getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
        // 给底部3个标签设置样式
        tabLayout.getTabAt(0).setCustomView(pagerAdapter.getTabView(0));
        tabLayout.getTabAt(1).setCustomView(pagerAdapter.getTabView(1));
        tabLayout.getTabAt(2).setCustomView(pagerAdapter.getTabView(2));
        tabLayout.addOnTabSelectedListener(new OnTabLayoutTabSelectListener() {
            @Override
            public void selectTab(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchFragment(position);
            }
            @Override
            public void unSelectTab(TabLayout.Tab tab) {
                int position = tab.getPosition();
                pagerAdapter.onTabUnSelected(position);
            }
        });

        switchFragment(0);
    }

    public void switchFragment(int position) {
        pagerAdapter.onTabSelected(position);
        viewPager.setCurrentItem(position,false);
        switch (position) {
            case 0:
                immersionBar.statusBarColor(R.color.status_bar_bg).init();
                break;
            case 1:
                immersionBar.statusBarColor(R.color.status_bar_bg).init();
                break;
            case 2:
                immersionBar.statusBarColor(R.color.txt_change).init();
                break;
        }

    }
}