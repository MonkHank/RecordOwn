package com.monk.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.luojilab.router.facade.annotation.RouteNode;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.commonutils.LogUtils;
import com.monk.ui.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

@RouteNode(path = "/uirouter/demo" ,desc = "module_home主页")
public class HomeActivity extends BaseCompatActivity<HomeActivity> {

    TabLayout tabLayout;
    NoScrollViewPager viewPager;

    private MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 组件化中不能使用同名的布局文件，必须唯一，否则布局id找不到
        initToolbar(R.layout.activity_home_home);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        Fragment fragmentHome = FragmentHome.newFragment();
        Fragment fragmentDevelop = FragmentDevelop.newFragment();
        Fragment fragmentMine = FragmentMine.newFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentDevelop);
        fragmentList.add(fragmentMine);

        LogUtils.i("tag","viewPager = "+viewPager);
        pagerAdapter = new MainPagerAdapter(getMContext(),getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
//         给底部3个标签设置样式
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
                getImmersionBar().statusBarColor(R.color.status_bar_bg).init();
                break;
            case 1:
                getImmersionBar().statusBarColor(R.color.status_bar_bg).init();
                break;
            case 2:
                getImmersionBar().statusBarColor(R.color.status_bar_bg).init();
                break;
        }

    }
}