package com.monk.commonutils.datepicker

import android.content.Context
import android.widget.TextView
import com.monk.commonutils.LogUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils(private val mContext: Context) {
    private val tag = "DateUtils"
    fun initDatePicker(tv: TextView, resultHandler: CustomDatePicker.ResultHandler?): CustomDatePicker {
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        val customDatePicker = CustomDatePicker(mContext, resultHandler, "2010-01-01 00:00", "2099-12-31 00:00")
        customDatePicker.setIsLoop(true)
        customDatePicker.showSpecificTime(false)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        val now = sdf.format(Date())
        tv.text = now.split(" ".toRegex()).toTypedArray()[0]
        return customDatePicker
    }

    /**
     * 当前时间
     *
     * @return 2018-02-07 16:26
     */
    val currentTime: String
        get() {
            val now = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
            val dateStr = sdf.format(now.timeInMillis)
            LogUtil.i(tag, "currentTime = $dateStr")
            return dateStr
        }

    companion object {
        const val NOW = 0

        /**
         * 返回两个日期之间相差几天(结束日期-开始日期)
         *
         * @param fDate 开始日期 2017-10-10
         * @param oDate 结束日期 2017-10-16
         * @return 天数
         */
        fun daysOfTwo(fDate: String?, oDate: String?): Int {
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val startTime = sdf.parse(fDate).time
                val endTime = sdf.parse(oDate).time
                ((endTime - startTime) / (1000 * 3600 * 24)).toInt()
            } catch (e: Exception) {
                -1
            }
        }

        /**
         * @param sTime 2004-03-26 13:31
         * @param eTime 2004-01-02 11:30
         * @return 小时
         */
        fun hoursOfTwo(sTime: String?, eTime: String?): Int {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            return try {
                val d1 = df.parse(sTime)
                val d2 = df.parse(eTime)
                val diff = d2.time - d1.time
                val days = diff / (1000 * 60 * 60 * 24)
                val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                val minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
                (if (minutes > 0) days * 24 + hours + 1 else days * 24 + hours).toInt()
            } catch (e: Exception) {
                -1
            }
        }

        /**
         * @param sTime 2004-03-26 13:31
         * @param eTime 2004-03-26 13:30
         * @return 分钟
         */
        fun minutesOfTwo(sTime: String?, eTime: String?): Int {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            return try {
                val d1 = df.parse(sTime)
                val d2 = df.parse(eTime)
                val diff = d2.time - d1.time
                val days = diff / (1000 * 60 * 60 * 24)
                val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                val minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
                minutes.toInt()
            } catch (e: Exception) {
                -1
            }
        }
    }
}