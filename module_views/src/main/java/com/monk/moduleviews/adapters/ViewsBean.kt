package com.monk.moduleviews.adapters

/**
 * @author monk
 * @date 2019-05-27
 */
data class ViewsBean( val Tag: String, var Name: String) {
    companion object{
        const val ed="event_dispatch"
        const val lf="layout_inflater"
        const val ae="add_equip"
        const val pc="percent_circle"
        const val mhv="max_height_view"
        const val qqlv="qq_lv"
    }
}