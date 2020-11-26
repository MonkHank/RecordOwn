package com.monk.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<String> tabsTextList;

    private int[] drawableUnselected = {R.mipmap.workingstation2, R.mipmap.record2, R.mipmap.mine2};
    private int[] drawableSelected = {R.mipmap.workingstation, R.mipmap.record, R.mipmap.mine};

    private Context context;
    private List<Fragment> list;
    private ImageView[] imageViews;
    private TextView[] tvs;

    @SuppressLint("WrongConstant")
    public MainPagerAdapter(Context context, @NonNull FragmentManager fm, List<Fragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.list = list;
        tabsTextList = Arrays.asList(
                context.getString(R.string.working_station),
                context.getString(R.string.record),
                context.getString(R.string.mine));
        imageViews = new ImageView[list.size()];
        tvs = new TextView[list.size()];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    /**
     * 初始化
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_tab, null);
        ImageView ivLogo = view.findViewById(R.id.ivLogo);
        TextView tvText = view.findViewById(R.id.tvText);

        ivLogo.setImageResource(drawableUnselected[position]);
        tvText.setText(tabsTextList.get(position));

        if (position == 0) {
            ivLogo.setImageResource(drawableSelected[0]);
            tvText.setTextColor(context.getResources().getColor(R.color.txt_change));
        }

        imageViews[position] = ivLogo;
        tvs[position] = tvText;

        return view;
    }

    public void onTabSelected(int position) {
        imageViews[position].setImageResource(drawableSelected[position]);
        tvs[position].setTextColor(context.getResources().getColor(R.color.txt_change));
    }

    public void onTabUnSelected(int position) {
        imageViews[position].setImageResource(drawableUnselected[position]);
        tvs[position].setTextColor(Color.argb(255,51,51,51));
    }
}
