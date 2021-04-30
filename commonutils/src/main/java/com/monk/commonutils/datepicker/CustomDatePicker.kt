package com.monk.commonutils.datepicker

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.monk.commonutils.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author liuwan
 * @date 2016/9/28.
 */
class CustomDatePicker(context: Context?, resultHandler: ResultHandler?, startDate: String, endDate: String) {
    /**
     * 定义结果回调接口
     */
    interface ResultHandler {
        fun handle(time: String?)
    }

    private enum class SCROLL_TYPE(var value: Int) {
        /**
         *
         */
        HOUR(1), MINUTE(2);
    }

    private var scrollUnits = SCROLL_TYPE.HOUR.value + SCROLL_TYPE.MINUTE.value
    private var handler: ResultHandler? = null
    private var context: Context? = null
    private var canAccess = false
    private var datePickerDialog: Dialog? = null
    private var dpvYear: DatePickerView? = null
    private var dpvMonth: DatePickerView? = null
    private var dpvDay: DatePickerView? = null
    private var dpvHour: DatePickerView? = null
    private var dpvMinutes: DatePickerView? = null
    private  var year: ArrayList<String>? = null
    private  var month: ArrayList<String>? = null
    private  var day: ArrayList<String>? = null
    private  var hour: ArrayList<String>? = null
    private  var minute: ArrayList<String>? = null
    private var startYear = 0
    private var startMonth = 0
    private var startDay = 0
    private var startHour = 0
    private var startMinute = 0
    private var endYear = 0
    private var endMonth = 0
    private var endDay = 0
    private var endHour = 0
    private var endMinute = 0
    private var spanYear = false
    private var spanMon = false
    private var spanDay = false
    private var spanHour = false
    private var spanMin = false
    private var selectedCalender: Calendar? = null
    private var startCalendar: Calendar? = null
    private var endCalendar: Calendar? = null
    private var tvCancel: TextView? = null
    private var tvSelect: TextView? = null
    private var tvHour: TextView? = null
    private var tvMinutes: TextView? = null
    private var tvYear: TextView? = null
    private var tvMonth: TextView? = null
    private var tvDay: TextView? = null
    private var isSpecificMinutes = false

    companion object {
        private const val MAX_MINUTE = 59
        private const val MAX_HOUR = 23
        private const val MIN_MINUTE = 0
        private const val MIN_HOUR = 0
        private const val MAX_MONTH = 12
    }

    init {
        if (isValidDate(startDate, "yyyy-MM-dd HH:mm") && isValidDate(endDate, "yyyy-MM-dd HH:mm")) {
            canAccess = true
            this.context = context
            handler = resultHandler
            selectedCalender = Calendar.getInstance()
            startCalendar = Calendar.getInstance()
            endCalendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
            try {
                startCalendar?.setTime(sdf.parse(startDate))
                endCalendar?.setTime(sdf.parse(endDate))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            initDialog()
            initView()
        }
    }

    private fun initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = Dialog(context!!, R.style.time_dialog)
            datePickerDialog!!.setCancelable(false)
            datePickerDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            datePickerDialog!!.setContentView(R.layout.custom_date_picker)
            val window = datePickerDialog!!.window
            window!!.setGravity(Gravity.BOTTOM)
            val manager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            manager.defaultDisplay.getMetrics(dm)
            val lp = window.attributes
            lp.width = dm.widthPixels
            window.attributes = lp
        }
    }

    private fun initView() {
        dpvYear = datePickerDialog!!.findViewById(R.id.year_pv)
        dpvMonth = datePickerDialog!!.findViewById(R.id.month_pv)
        dpvDay = datePickerDialog!!.findViewById(R.id.day_pv)
        dpvHour = datePickerDialog!!.findViewById(R.id.hour_pv)
        dpvMinutes = datePickerDialog!!.findViewById(R.id.minute_pv)
        tvCancel = datePickerDialog!!.findViewById(R.id.tv_cancle)
        tvSelect = datePickerDialog!!.findViewById(R.id.tv_select)
        tvHour = datePickerDialog!!.findViewById(R.id.hour_text)
        tvMinutes = datePickerDialog!!.findViewById(R.id.minute_text)
        // TODO: 2017-10-23 2017-10-23 13:06:57书写
        tvYear = datePickerDialog!!.findViewById(R.id.year_text)
        tvMonth = datePickerDialog!!.findViewById(R.id.month_text)
        tvDay = datePickerDialog!!.findViewById(R.id.day_text)
        tvCancel?.setOnClickListener(View.OnClickListener { datePickerDialog!!.dismiss() })
        tvSelect?.setOnClickListener(View.OnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
            handler!!.handle(sdf.format(selectedCalender!!.time))
            datePickerDialog!!.dismiss()
        })
    }

    private fun initParameter() {
        startYear = startCalendar!![Calendar.YEAR]
        startMonth = startCalendar!![Calendar.MONTH] + 1
        startDay = startCalendar!![Calendar.DAY_OF_MONTH]
        startHour = startCalendar!![Calendar.HOUR_OF_DAY]
        startMinute = startCalendar!![Calendar.MINUTE]
        endYear = endCalendar!![Calendar.YEAR]
        endMonth = endCalendar!![Calendar.MONTH] + 1
        endDay = endCalendar!![Calendar.DAY_OF_MONTH]
        endHour = endCalendar!![Calendar.HOUR_OF_DAY]
        endMinute = endCalendar!![Calendar.MINUTE]
        spanYear = startYear != endYear
        spanMon = !spanYear && startMonth != endMonth
        spanDay = !spanMon && startDay != endDay
        spanHour = !spanDay && startHour != endHour
        spanMin = !spanHour && startMinute != endMinute
        selectedCalender!!.time = startCalendar!!.time
    }

    private fun initTimer() {
        initArrayList()
        if (spanYear) {
            for (i in startYear..endYear) {
                year!!.add(i.toString())
            }
            for (i in startMonth..MAX_MONTH) {
                month!!.add(formatTimeUnit(i))
            }
            for (i in startDay..startCalendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day!!.add(formatTimeUnit(i))
            }
            if (scrollUnits and SCROLL_TYPE.HOUR.value != SCROLL_TYPE.HOUR.value) {
                hour!!.add(formatTimeUnit(startHour))
            } else {
                for (i in startHour..MAX_HOUR) {
                    hour!!.add(formatTimeUnit(i))
                }
            }
            if (scrollUnits and SCROLL_TYPE.MINUTE.value != SCROLL_TYPE.MINUTE.value) {
                minute!!.add(formatTimeUnit(startMinute))
            } else {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            }
        } else if (spanMon) {
            year!!.add(startYear.toString())
            for (i in startMonth..endMonth) {
                month!!.add(formatTimeUnit(i))
            }
            for (i in startDay..startCalendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day!!.add(formatTimeUnit(i))
            }
            if (scrollUnits and SCROLL_TYPE.HOUR.value != SCROLL_TYPE.HOUR.value) {
                hour!!.add(formatTimeUnit(startHour))
            } else {
                for (i in startHour..MAX_HOUR) {
                    hour!!.add(formatTimeUnit(i))
                }
            }
            if (scrollUnits and SCROLL_TYPE.MINUTE.value != SCROLL_TYPE.MINUTE.value) {
                minute!!.add(formatTimeUnit(startMinute))
            } else {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            }
        } else if (spanDay) {
            year!!.add(startYear.toString())
            month!!.add(formatTimeUnit(startMonth))
            for (i in startDay..endDay) {
                day!!.add(formatTimeUnit(i))
            }
            if (scrollUnits and SCROLL_TYPE.HOUR.value != SCROLL_TYPE.HOUR.value) {
                hour!!.add(formatTimeUnit(startHour))
            } else {
                for (i in startHour..MAX_HOUR) {
                    hour!!.add(formatTimeUnit(i))
                }
            }
            if (scrollUnits and SCROLL_TYPE.MINUTE.value != SCROLL_TYPE.MINUTE.value) {
                minute!!.add(formatTimeUnit(startMinute))
            } else {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            }
        } else if (spanHour) {
            year!!.add(startYear.toString())
            month!!.add(formatTimeUnit(startMonth))
            day!!.add(formatTimeUnit(startDay))
            if (scrollUnits and SCROLL_TYPE.HOUR.value != SCROLL_TYPE.HOUR.value) {
                hour!!.add(formatTimeUnit(startHour))
            } else {
                for (i in startHour..endHour) {
                    hour!!.add(formatTimeUnit(i))
                }
            }
            if (scrollUnits and SCROLL_TYPE.MINUTE.value != SCROLL_TYPE.MINUTE.value) {
                minute!!.add(formatTimeUnit(startMinute))
            } else {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            }
        } else if (spanMin) {
            year!!.add(startYear.toString())
            month!!.add(formatTimeUnit(startMonth))
            day!!.add(formatTimeUnit(startDay))
            hour!!.add(formatTimeUnit(startHour))
            if (scrollUnits and SCROLL_TYPE.MINUTE.value != SCROLL_TYPE.MINUTE.value) {
                minute!!.add(formatTimeUnit(startMinute))
            } else {
                for (i in startMinute..endMinute) {
                    minute!!.add(formatTimeUnit(i))
                }
            }
        }
        loadComponent()
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private fun formatTimeUnit(unit: Int): String {
        return if (unit < 10) "0$unit" else unit.toString()
    }

    private fun initArrayList() {
        if (year == null) {
            year = ArrayList()
        }
        if (month == null) {
            month = ArrayList()
        }
        if (day == null) {
            day = ArrayList()
        }
        if (hour == null) {
            hour = ArrayList()
        }
        if (minute == null) {
            minute = ArrayList()
        }
        year!!.clear()
        month!!.clear()
        day!!.clear()
        hour!!.clear()
        minute!!.clear()
    }

    private fun loadComponent() {
        dpvYear!!.setData(year!!)
        dpvMonth!!.setData(month!!)
        dpvDay!!.setData(day!!)
        dpvHour!!.setData(hour!!)
        dpvMinutes!!.setData(minute!!)
        dpvYear!!.setSelected(0)
        dpvMonth!!.setSelected(0)
        dpvDay!!.setSelected(0)
        dpvHour!!.setSelected(0)
        dpvMinutes!!.setSelected(0)
        executeScroll()
    }

    private fun addListener() {
        dpvYear?.setOnSelectListener(object : DatePickerView.onSelectListener{
            override fun onSelect(text: String?) {
                selectedCalender!![Calendar.YEAR] = text!!.toInt()
                monthChange()
            }
        })
        dpvMonth!!.setOnSelectListener(object :DatePickerView.onSelectListener{
            override fun onSelect(text: String?) {
                selectedCalender!![Calendar.DAY_OF_MONTH] = 1
                selectedCalender!![Calendar.MONTH] = text!!.toInt() - 1
                dayChange()
            }
        })
        dpvDay!!.setOnSelectListener(object :DatePickerView.onSelectListener{
            override fun onSelect(text: String?) {
                selectedCalender!![Calendar.DAY_OF_MONTH] = text!!.toInt()
                hourChange()
            }

        })
        dpvHour!!.setOnSelectListener(object :DatePickerView.onSelectListener{
            override fun onSelect(text: String?) {
                selectedCalender!![Calendar.HOUR_OF_DAY] = text!!.toInt()
                minuteChange()
            }

        })
        dpvMinutes!!.setOnSelectListener(object :DatePickerView.onSelectListener{
            override fun onSelect(text: String?) {
                selectedCalender!![Calendar.MINUTE] = text!!.toInt()
            }

        })
    }

    private fun monthChange() {
        month!!.clear()
        val selectedYear = selectedCalender!![Calendar.YEAR]
        if (selectedYear == startYear) {
            for (i in startMonth..MAX_MONTH) {
                month!!.add(formatTimeUnit(i))
            }
        } else if (selectedYear == endYear) {
            for (i in 1..endMonth) {
                month!!.add(formatTimeUnit(i))
            }
        } else {
            for (i in 1..MAX_MONTH) {
                month!!.add(formatTimeUnit(i))
            }
        }
        selectedCalender!![Calendar.MONTH] = month!![0].toInt() - 1
        dpvMonth!!.setData(month!!)
        dpvMonth!!.setSelected(0)
        executeAnimator(dpvMonth)
        dpvMonth!!.postDelayed({ dayChange() }, 100)
    }

    private fun dayChange() {
        day!!.clear()
        val selectedYear = selectedCalender!![Calendar.YEAR]
        val selectedMonth = selectedCalender!![Calendar.MONTH] + 1
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (i in startDay..selectedCalender!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day!!.add(formatTimeUnit(i))
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (i in 1..endDay) {
                day!!.add(formatTimeUnit(i))
            }
        } else {
            for (i in 1..selectedCalender!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day!!.add(formatTimeUnit(i))
            }
        }
        selectedCalender!![Calendar.DAY_OF_MONTH] = day!![0].toInt()
        dpvDay!!.setData(day!!)
        dpvDay!!.setSelected(0)
        executeAnimator(dpvDay)
        dpvDay!!.postDelayed({ hourChange() }, 100)
    }

    private fun hourChange() {
        if (scrollUnits and SCROLL_TYPE.HOUR.value == SCROLL_TYPE.HOUR.value) {
            hour!!.clear()
            val selectedYear = selectedCalender!![Calendar.YEAR]
            val selectedMonth = selectedCalender!![Calendar.MONTH] + 1
            val selectedDay = selectedCalender!![Calendar.DAY_OF_MONTH]
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                for (i in startHour..MAX_HOUR) {
                    hour!!.add(formatTimeUnit(i))
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                for (i in MIN_HOUR..endHour) {
                    hour!!.add(formatTimeUnit(i))
                }
            } else {
                for (i in MIN_HOUR..MAX_HOUR) {
                    hour!!.add(formatTimeUnit(i))
                }
            }
            selectedCalender!![Calendar.HOUR_OF_DAY] = hour!![0].toInt()
            dpvHour!!.setData(hour!!)
            dpvHour!!.setSelected(0)
            executeAnimator(dpvHour)
        }
        dpvHour!!.postDelayed({ minuteChange() }, 100)
    }

    private fun minuteChange() {
        if (scrollUnits and SCROLL_TYPE.MINUTE.value == SCROLL_TYPE.MINUTE.value) {
            minute!!.clear()
            val selectedYear = selectedCalender!![Calendar.YEAR]
            val selectedMonth = selectedCalender!![Calendar.MONTH] + 1
            val selectedDay = selectedCalender!![Calendar.DAY_OF_MONTH]
            val selectedHour = selectedCalender!![Calendar.HOUR_OF_DAY]
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                for (i in MIN_MINUTE..endMinute) {
                    minute!!.add(formatTimeUnit(i))
                }
            } else {
                if (isSpecificMinutes) {
                    minute!!.add("00")
                    minute!!.add("30")
                } else {
                    for (i in startMinute..MAX_MINUTE) {
                        minute!!.add(formatTimeUnit(i))
                    }
                }
            }
            selectedCalender!![Calendar.MINUTE] = minute!![0].toInt()
            dpvMinutes!!.setData(minute!!)
            dpvMinutes!!.setSelected(0)
            executeAnimator(dpvMinutes)
        }
        executeScroll()
    }

    private fun executeAnimator(view: View?) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }

    private fun executeScroll() {
        dpvYear!!.setCanScroll(year!!.size > 1)
        dpvMonth!!.setCanScroll(month!!.size > 1)
        dpvDay!!.setCanScroll(day!!.size > 1)
        dpvHour!!.setCanScroll(hour!!.size > 1 && scrollUnits and SCROLL_TYPE.HOUR.value == SCROLL_TYPE.HOUR.value)
        dpvMinutes!!.setCanScroll(minute!!.size > 1 && scrollUnits and SCROLL_TYPE.MINUTE.value == SCROLL_TYPE.MINUTE.value)
    }

    private fun disScrollUnit(vararg scroll_types: SCROLL_TYPE): Int {
        if (scroll_types == null || scroll_types.size == 0) {
            scrollUnits = SCROLL_TYPE.HOUR.value + SCROLL_TYPE.MINUTE.value
        } else {
            for (scroll_type in scroll_types) {
                scrollUnits = scrollUnits xor scroll_type.value
            }
        }
        return scrollUnits
    }

    fun show(time: String) {
        if (canAccess) {
            if (isValidDate(time, "yyyy-MM-dd")) {
                if (startCalendar!!.time.time < endCalendar!!.time.time) {
                    canAccess = true
                    initParameter()
                    initTimer()
                    addListener()
                    setSelectedTime(time)
                    datePickerDialog!!.show()
                }
            } else {
                canAccess = false
            }
        }
    }

    /**
     * 设置日期控件是否显示时和分
     */
    fun showSpecificTime(show: Boolean) {
        if (canAccess) {
            if (show) {
                disScrollUnit()
                dpvHour!!.visibility = View.VISIBLE //日 后面的数字（时）
                tvHour!!.visibility = View.VISIBLE // 时
                dpvMinutes!!.visibility = View.VISIBLE //分钟数字
                tvMinutes!!.visibility = View.VISIBLE //分
            } else {
                disScrollUnit(SCROLL_TYPE.HOUR, SCROLL_TYPE.MINUTE)
                dpvHour!!.visibility = View.GONE
                tvHour!!.visibility = View.GONE
                dpvMinutes!!.visibility = View.GONE
                tvMinutes!!.visibility = View.GONE
            }
        }
    }

    /**
     * 设置日期控件是否显示年月日
     */
    fun showSpecificDate(show: Boolean) {
        if (canAccess) {
            if (show) {
                dpvYear!!.visibility = View.VISIBLE
                tvYear!!.visibility = View.VISIBLE
                dpvMonth!!.visibility = View.VISIBLE
                tvMonth!!.visibility = View.VISIBLE
                dpvDay!!.visibility = View.VISIBLE
                tvDay!!.visibility = View.VISIBLE
            } else {
                dpvYear!!.visibility = View.GONE
                tvYear!!.visibility = View.GONE
                dpvMonth!!.visibility = View.GONE
                tvMonth!!.visibility = View.GONE
                dpvDay!!.visibility = View.GONE
                tvDay!!.visibility = View.GONE
            }
        }
    }

    /**
     * 设置日期控件是否显示年月日
     */
    fun showSpecificDay(show: Boolean) {
        if (canAccess) {
            if (show) {
                dpvYear!!.visibility = View.VISIBLE
                tvYear!!.visibility = View.VISIBLE
                dpvMonth!!.visibility = View.VISIBLE
                tvMonth!!.visibility = View.VISIBLE
                dpvDay!!.visibility = View.VISIBLE
                tvDay!!.visibility = View.VISIBLE
            } else {
                dpvYear!!.visibility = View.VISIBLE
                tvYear!!.visibility = View.VISIBLE
                dpvMonth!!.visibility = View.VISIBLE
                tvMonth!!.visibility = View.VISIBLE
                dpvDay!!.visibility = View.GONE
                tvDay!!.visibility = View.GONE
            }
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    fun setIsLoop(isLoop: Boolean) {
        if (canAccess) {
            dpvYear!!.setIsLoop(isLoop)
            dpvMonth!!.setIsLoop(isLoop)
            dpvDay!!.setIsLoop(isLoop)
            dpvHour!!.setIsLoop(isLoop)
            dpvMinutes!!.setIsLoop(isLoop)
        }
    }

    /**
     * 设置日期控件默认选中的时间
     */
    fun setSelectedTime(time: String) {
        if (canAccess) {
            val str = time.split(" ".toRegex()).toTypedArray()
            val dateStr = str[0].split("-".toRegex()).toTypedArray()
            dpvYear!!.setSelected(dateStr[0])
            selectedCalender!![Calendar.YEAR] = dateStr[0].toInt()
            month!!.clear()
            val selectedYear = selectedCalender!![Calendar.YEAR]
            if (selectedYear == startYear) {
                for (i in startMonth..MAX_MONTH) {
                    month!!.add(formatTimeUnit(i))
                }
            } else if (selectedYear == endYear) {
                for (i in 1..endMonth) {
                    month!!.add(formatTimeUnit(i))
                }
            } else {
                for (i in 1..MAX_MONTH) {
                    month!!.add(formatTimeUnit(i))
                }
            }
            dpvMonth!!.setData(month!!)
            dpvMonth!!.setSelected(dateStr[1])
            selectedCalender!![Calendar.MONTH] = dateStr[1].toInt() - 1
            executeAnimator(dpvMonth)
            day!!.clear()
            val selectedMonth = selectedCalender!![Calendar.MONTH] + 1
            if (selectedYear == startYear && selectedMonth == startMonth) {
                for (i in startDay..selectedCalender!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    day!!.add(formatTimeUnit(i))
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth) {
                for (i in 1..endDay) {
                    day!!.add(formatTimeUnit(i))
                }
            } else {
                for (i in 1..selectedCalender!!.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    day!!.add(formatTimeUnit(i))
                }
            }
            dpvDay!!.setData(day!!)
            dpvDay!!.setSelected(dateStr[2])
            selectedCalender!![Calendar.DAY_OF_MONTH] = dateStr[2].toInt()
            executeAnimator(dpvDay)
            if (str.size == 2) {
                val timeStr = str[1].split(":".toRegex()).toTypedArray()
                if (scrollUnits and SCROLL_TYPE.HOUR.value == SCROLL_TYPE.HOUR.value) {
                    hour!!.clear()
                    val selectedDay = selectedCalender!![Calendar.DAY_OF_MONTH]
                    if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                        for (i in startHour..MAX_HOUR) {
                            hour!!.add(formatTimeUnit(i))
                        }
                    } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                        for (i in MIN_HOUR..endHour) {
                            hour!!.add(formatTimeUnit(i))
                        }
                    } else {
                        for (i in MIN_HOUR..MAX_HOUR) {
                            hour!!.add(formatTimeUnit(i))
                        }
                    }
                    dpvHour!!.setData(hour!!)
                    dpvHour!!.setSelected(timeStr[0])
                    selectedCalender!![Calendar.HOUR_OF_DAY] = timeStr[0].toInt()
                    executeAnimator(dpvHour)
                }
                if (scrollUnits and SCROLL_TYPE.MINUTE.value == SCROLL_TYPE.MINUTE.value) {
                    minute!!.clear()
                    val selectedDay = selectedCalender!![Calendar.DAY_OF_MONTH]
                    val selectedHour = selectedCalender!![Calendar.HOUR_OF_DAY]
                    if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                        if (isSpecificMinutes) {
                            minute!!.add("00")
                            minute!!.add("30")
                        } else {
                            for (i in startMinute..MAX_MINUTE) {
                                minute!!.add(formatTimeUnit(i))
                            }
                        }
                    } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                        for (i in MIN_MINUTE..endMinute) {
                            minute!!.add(formatTimeUnit(i))
                        }
                    } else {
                        if (isSpecificMinutes) {
                            minute!!.add("00")
                            minute!!.add("30")
                        } else {
                            for (i in startMinute..MAX_MINUTE) {
                                minute!!.add(formatTimeUnit(i))
                            }
                        }
                    }
                    dpvMinutes!!.setData(minute!!)
                    dpvMinutes!!.setSelected(timeStr[1])
                    selectedCalender!![Calendar.MINUTE] = timeStr[1].toInt()
                    executeAnimator(dpvMinutes)
                }
            }
            executeScroll()
        }
    }

    /**
     * 验证字符串是否是一个合法的日期格式
     */
    private fun isValidDate(date: String, template: String): Boolean {
        var convertSuccess = true
        // 指定日期格式
        val format = SimpleDateFormat(template, Locale.CHINA)
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.isLenient = false
            format.parse(date)
        } catch (e: Exception) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false
        }
        return convertSuccess
    }

    fun setIsSpecificMinutes(isSpecificMinutes: Boolean) {
        this.isSpecificMinutes = isSpecificMinutes
    }


}