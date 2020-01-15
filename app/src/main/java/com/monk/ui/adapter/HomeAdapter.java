package com.monk.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;
import com.monk.ui.HomeBean;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener;
import com.monk.ui.interfaces.OnRecyclerViewItemClickListener2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author monk
 * @date 2019-05-27
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private Context context;
    private List<HomeBean> list;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemClickListener2 onRecyclerViewItemClickListener2;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public HomeAdapter(Context context, List<HomeBean> list, OnRecyclerViewItemClickListener2 onRecyclerViewItemClickListener2) {
        this.context = context;
        this.list = list;
        this.onRecyclerViewItemClickListener2 = onRecyclerViewItemClickListener2;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_home, parent, false);
        // 第一种方式的回调，会被第二种拦截
        view.setOnClickListener(v -> {
            if (onRecyclerViewItemClickListener != null) {
                onRecyclerViewItemClickListener.onRecyclerViewItemClick(v);
            }
        });
        return new ViewHolder(view, onRecyclerViewItemClickListener2);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeBean bean = list.get(position);
        holder.tvContactName.setText(bean.Name);
        int adapterPosition = holder.getAdapterPosition();
        int layoutPosition = holder.getLayoutPosition();
        LogUtil.i("HomeAdapter","adapterPosition = "+adapterPosition+"\tlayoutPosition = "+layoutPosition+"\tposition = "+position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 还有一个重点是静态内部类的对象不持有外部类的引用，非静态内部了持有外部类的引用。
     * 并不是所有的内部类只能使用静态内部类，只有在该内部类中的生命周期不可控制的情况下，我们要采用静态内部类，其它时候大家可以照旧。
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contacts_name)
        TextView tvContactName;

        ViewHolder(@NonNull View itemView, OnRecyclerViewItemClickListener2 onRecyclerViewItemClickListener2) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // 第二种方式，其实和第一种方式本质是一样的
            itemView.setOnClickListener(v ->
                    onRecyclerViewItemClickListener2.onRecyclerViewItemClick(itemView, getAdapterPosition()));

        }
    }
}
