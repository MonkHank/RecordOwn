package com.monk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monk.activity.AidlFullscreenActivity;
import com.monk.activity.LoginActivity;
import com.monk.activity.ScrollingActivity;
import com.monk.aidldemo.R;
import com.monk.base.BaseCompatActivity;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;
import com.monk.eventdispatch.EventDispatchActivity;
import com.monk.ui.adapter.HomeAdapter;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener2;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author monk
 * @date 2019-05-27
 */
public class HomeActivity extends BaseCompatActivity implements OnRecyclerViewItemClickListener,
        OnRecyclerViewItemClickListener2 {

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<String> list = Arrays.asList("ManiActivity", "JniActivity", "AidlDemo",
            "ScrollbarActivity","EventDispatchActivity","DemoLoginActivity",
            "1","2","3",
            "4","5","6",
            "7","8","9",
            "10","11","12",
            "13","14","15",
            "16","17","18",
            "19","20");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 设置导航默认是箭头，id是android.R.id.home
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 将箭头设置成我们的图片
//            actionBar.setHomeAsUpIndicator();
        }

        navigationView.setCheckedItem(R.id.nav_call);
        View headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.icon_image).setOnClickListener(v -> ToastUtils.showImageToast(mContext,"点击了头像"));
        headerView.findViewById(R.id.mail).setOnClickListener(v -> ToastUtils.showImageToast(mContext,"点击了邮箱"));
        headerView.findViewById(R.id.username).setOnClickListener(v -> ToastUtils.showImageToast(mContext,"点击了名字"));

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // 关闭侧滑
            drawerLayout.closeDrawers();
            return true;
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        HomeAdapter homeAdapter = new HomeAdapter(this, list,this);
        // setAdapter 为异步操作
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnRecyclerViewItemClickListener(this);
    }

    /**
     * 不会执行，已被第二种方式拦截
     * @param view
     */
    @Override
    public void onRecyclerViewItemClick(View view) {
        LogUtil.i(tag,"view:"+view);
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        LogUtil.i(tag,"position="+position);
        switch(position){
            case 0:
                startActivity(MainActivity.class);
                break;
            case 2:
                startActivity(AidlFullscreenActivity.class);
                break;
            case 3:
                ScrollingActivity.intoHere(mContext);
                break;
            case 4:
                startActivity(EventDispatchActivity.class);
                break;
            case 5:
                startActivity(LoginActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
