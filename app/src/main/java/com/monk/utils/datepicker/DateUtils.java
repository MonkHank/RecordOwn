package com.monk.utils.datepicker;

import android.content.Context;
import android.widget.TextView;

import com.monk.commonutils.LogUtil;

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
    public  int daysOfTwo(String fDate, String oDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long startTime = sdf.parse(fDate).getTime();
            long endTime = sdf.parse(oDate).getTime();
            return (int) ((endTime - startTime) / (1000 * 3600 * 24));
        } catch (Exception e) {
            return -1;
        }
    }
}
