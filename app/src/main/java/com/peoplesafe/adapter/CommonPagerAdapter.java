package com.peoplesafe.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author MonkeyHank
 * @date 2017-10-12 17:43
 */
public class CommonPagerAdapter extends PagerAdapter {
    private final List<? extends BasePage> mPagers;

    public CommonPagerAdapter(List<? extends BasePage> mPagers) {
        this.mPagers = mPagers;
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 这个方法是初始化viewPager的时候调用，一开始调用两次，预加载（0 和 1 页面）；
     * 如果viewPager一开始是隐藏的，那么是不会执行的；
     * 以后每滑动一次就提前加载下一个position的页面；
     *
     * @param container ViewPager
     * @param position from 0 started
     * @return basePage.mRootView ，需要在容器中显示的布局
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePage basePage = mPagers.get(position);
        View view = basePage.mRootView;
        container.addView(view);
        return view;
    }

    /**
     * 此方法是在滑动viewpager到第三个的时候会执行，将第一个的object移除掉，
     * 往后滑动依次移除从第一个往下的object;
     *
     * @param container ViewPager
     * @param position
     * @param object    basePage.mRootView，需要在容器中显示的布局
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
