package com.monk.moduleannotation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.ui.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseCompatActivity<HomeActivity> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_home);




    }

}