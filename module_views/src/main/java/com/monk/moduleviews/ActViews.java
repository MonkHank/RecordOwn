package com.monk.moduleviews;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.moduleviews.adapters.ViewsAdapter;
import com.monk.moduleviews.adapters.ViewsBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;


//@RouteNode(path = "/views/main", desc = "module_views主页")
public class ActViews extends BaseCompatActivity<ActViews> implements
        BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener {

    private TextView tvMsg;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.act_moduleviews_main);

        tvMsg = findViewById(R.id.tvMsg);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        List<ViewsBean> list = new ArrayList<>();
        list.add(new ViewsBean(ViewsBean.ed, "EventDispatch"));
        list.add(new ViewsBean(ViewsBean.lf, "LayoutInflater"));
        list.add(new ViewsBean(ViewsBean.ae, "AddEquipmentItem"));
        list.add(new ViewsBean(ViewsBean.pc, "PercentCircleView"));
        list.add(new ViewsBean(ViewsBean.mhv, "MaxHeightView"));
        list.add(new ViewsBean(ViewsBean.qqlv, "QQListView"));
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
        tvMsg.setText("view="+view+" position="+position);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        tvMsg.setText("view="+view+" position="+position);
        ViewsBean item = (ViewsBean) adapter.getItem(position);
        if (item==null)return;

        startActivity(ActViewsDetail.Companion.createIntent(this,item.getTag()));
    }
}
