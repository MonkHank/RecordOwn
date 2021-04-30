package com.monk.moduleviews;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luojilab.router.facade.annotation.RouteNode;
import com.monk.ViewsDetailActivity;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.moduleviews.adapters.ViewsAdapter;
import com.monk.moduleviews.adapters.ViewsBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;


@RouteNode(path = "/views/main", desc = "主页")
public class ViewsActivity extends BaseCompatActivity<ViewsActivity> implements
        BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_module_views_main);

        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        List<ViewsBean> list = new ArrayList<>();
        list.add(new ViewsBean(0, "AddEquipmentItem"));
        list.add(new ViewsBean(1, "PercentCircleView"));
        ViewsAdapter adapter = new ViewsAdapter(this, list);
//        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_divider_recycler_view));
        recyclerView.addItemDecoration(divider);


    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showLong("view="+view+" position="+position);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showLong("view="+view+" position="+position);
        startActivity(ViewsDetailActivity.Companion.createIntent(this,position));
    }
}
