package com.monk.moduleviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.datepicker.CustomDatePicker
import com.monk.commonutils.datepicker.DateUtils
import com.monk.moduleviews.R
import com.monk.moduleviews.views.AddEquipmentItem


/**
 * @author monk
 * @date 2019-05-29
 */
class FraViewsAddEquip : BaseFragment<FraViewsAddEquip?>() ,View.OnClickListener{

    private lateinit var flStartDate:AddEquipmentItem
    private lateinit var flEndDate:AddEquipmentItem
    private var flDays:AddEquipmentItem?=null
    private var tvStartDate: TextView? = null
    private  var tvEndDate:TextView? = null
    private var etDays: EditText? = null


    private var customDatePicker: CustomDatePicker? = null
    private  var customDatePicker1:CustomDatePicker? = null
    private var dateUtils: DateUtils? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fra_moduleviews_addequip, container, false)

        flStartDate = view.findViewById(R.id.flStartDate)
        flEndDate = view.findViewById(R.id.flEndDate)
        flDays = view.findViewById(R.id.flDays)

        tvStartDate = flStartDate.tvContent
        tvEndDate = flEndDate.tvContent
        etDays = flDays?.etContent

        flStartDate.setOnClickListener(this)
        flEndDate.setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateUtils = DateUtils(context!!)
        initStartDate()
        initEndDate()
    }

    private fun initStartDate() {
        customDatePicker = dateUtils?.initDatePicker(tvStartDate!!, object : CustomDatePicker.ResultHandler {
            override fun handle(time: String?) {
                val startDate = time?.split(" ".toRegex())!!.toTypedArray()[0]
                val endTime: String = tvEndDate?.text.toString()
                val daysOfTwo: Long = DateUtils.daysOfTwo(startDate, endTime).toLong()
                tvStartDate?.text = startDate
                etDays?.setText("$daysOfTwo 天 （不包含开始日期） ")
            }
        })
    }

    private fun initEndDate() {
        customDatePicker1 = dateUtils?.initDatePicker(tvEndDate!!, object : CustomDatePicker.ResultHandler {
            override fun handle(time: String?) {
                val startDate: String = tvStartDate?.text.toString()
                val endTime = time?.split(" ".toRegex())!!.toTypedArray()[0]
                val daysOfTwo: Long = DateUtils.daysOfTwo(startDate, endTime).toLong()
                if (daysOfTwo < 0) {
                    tvEndDate?.text = startDate
                    ToastUtils.showShort("结束日期不能小于开始日期")
                } else {
                    tvEndDate?.text = endTime
                }
                etDays?.setText("$daysOfTwo 天 （不包含开始日期）")
            }

        })
    }

    override fun onClick(v: View?) {
        val startDate = tvStartDate!!.text.toString()
        val endDate = tvEndDate!!.text.toString()
        when (v?.id) {
            R.id.flStartDate -> customDatePicker!!.show(startDate)
            R.id.flEndDate -> customDatePicker1!!.show(endDate)
        }
    }
}