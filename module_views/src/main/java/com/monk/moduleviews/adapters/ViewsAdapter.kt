package com.monk.moduleviews.adapters

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.monk.moduleviews.R

class ViewsAdapter(private val context: Context, data: List<ViewsBean>?)
    : BaseQuickAdapter<ViewsBean, BaseViewHolder>(R.layout.views_item_views, data) {

    override fun convert(helper: BaseViewHolder, item: ViewsBean) {
        helper.setText(R.id.tvTitle, item.Name)
    }

}