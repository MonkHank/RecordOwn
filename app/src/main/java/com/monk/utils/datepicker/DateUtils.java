package com.monk.utils.datepicker;

import android.content.Context;
import android.widget.TextView;

import com.monk.commonutils.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private final String tag = "DateUtils";

    private Context mContext;

    public static final int NOW = 0;


    public DateUtils(Context context) {
        mContext = context;
    }

    public CustomDatePicker initDatePicker(TextView tv,  CustomDatePicker.ResultHandler resultHandler) {
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        CustomDatePicker customDatePicker = new CustomDatePicker(mContext, resultHandler, "2010-01-01 00:00", "2099-12-31 00:00");
        customDatePicker.setIsLoop(true);
        customDatePicker.showSpecificTime(false);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        tv.setText(now.split(" ")[0]);

        return customDatePicker;
    }

    /**
     * 当前时间
     *
     * @return 2018-02-07 16:26
     */
    public  String getCurrentTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String dateStr = sdf.format(now.getTimeInMillis());
        LogUtil.i(tag, "currentTime = " + dateStr);
        return dateStr;
    }

    /**
     * 返回两个日期之间相差几天(结束日期-开始日期)
     *
     * @param fDate 开始日期 2017-10-10
     * @param oDate 结束日期 2017-10-16
     * @return 天数
     */
    public static int daysOfTwo(String fDate, String oDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long startTime = sdf.parse(fDate).getTime();
            long endTime = sdf.parse(oDate).getTime();
            return (int) ((endTime - startTime) / (1000 * 3600 * 24));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param sTime 2004-03-26 13:31
     * @param eTime 2004-01-02 11:30
     * @return 小时
     */
    public static int hoursOfTwo(String sTime, String eTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = df.parse(sTime);
            Date d2 = df.parse(eTime);
            long diff = d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return (int) (minutes > 0 ? days * 24 + hours + 1 : days * 24 + hours);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param sTime 2004-03-26 13:31
     * @param eTime 2004-03-26 13:30
     * @return 分钟
     */
    public static int minutesOfTwo(String sTime, String eTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date d1 = df.parse(sTime);
            Date d2 = df.parse(eTime);
            long diff = d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return (int) minutes ;
        } catch (Exception e) {
            return -1;
        }
    }
}
