package com.monk;

import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.monk.activity.AidlFullscreenActivity;
import com.monk.activity.LayoutInflaterActivity;
import com.monk.activity.LoginActivity;
import com.monk.aidldemo.R;
import com.monk.base.BaseCompatActivity;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;
import com.monk.eventdispatch.EventDispatchActivity;
import com.monk.ui.HomeBean;
import com.monk.ui.adapter.HomeAdapter;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author monk
 * @date 2019-05-27
 */
public class HomeActivity extends BaseCompatActivity implements OnRecyclerViewItemClickListener,
        OnRecyclerViewItemClickListener2 {

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<HomeBean> list =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_home);

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

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        list.add(new HomeBean(0,MainActivity.class.getSimpleName()));
        list.add(new HomeBean(4,LoginActivity.class.getSimpleName()));
        list.add(new HomeBean(1,AidlFullscreenActivity.class.getSimpleName()));
        list.add(new HomeBean(2,LayoutInflaterActivity.class.getSimpleName()));
        list.add(new HomeBean(3,EventDispatchActivity.class.getSimpleName()));
        list.add(new HomeBean(6,"kill MySelf"));
        HomeAdapter homeAdapter = new HomeAdapter(this, list,this);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnRecyclerViewItemClickListener(this);

    }

    /**
     * RecyclerView 的 itemClick 回调；不会执行，已被第二种方式拦截
     * @param view
     */
    @Override
    public void onRecyclerViewItemClick(View view) {
        LogUtil.i(simpleName,"view:"+view);
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        HomeBean bean = list.get(position);
        switch(bean.Tag){
            case 0:
                startActivity(MainActivity.class);
                break;
            case 1:
                startActivity(AidlFullscreenActivity.class);
                break;
            case 2:
                LayoutInflaterActivity.intoHere(mContext);
                break;
            case 3:
                startActivity(EventDispatchActivity.class);
                break;
            case 4:
                startActivity(LoginActivity.class);
                break;
            case 6:
                Process.killProcess(Process.myPid());
                break;
            default:
                break;
        }
    }

}
