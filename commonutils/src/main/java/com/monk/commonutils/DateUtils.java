package com.monk.commonutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author MonkeyHank
 * @date 2017-10-16 18:08
 */

public final class DateUtils {
    private final static String tag = "DateUtils";
    private static final String DATE_TIME_SPLIT = " ";

    /**
     * 此函数实现：给定日期和经过天数，算出结果日期
     *
     * @param sDate 指定日期，
     * @param iDate 多少时间段（可以是 年、月、日...  具体根据iCal来确定）
     * @param iCal  某种时间段例如  月：Calendar.MONTH（具体可查询api中Calendar类）
     * @param sStr  日期格式 例如："yyyyMMdd"（具体可查询api中Calendar类）
     * @return
     */
    public static String getNextDate(String sDate, int iDate, int iCal, String sStr) {
        String sNextDate = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(sStr);
        Date date = null;
        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(iCal, iDate);
        sNextDate = formatter.format(calendar.getTime());
        return sNextDate;
    }

    /**
     * 当前时间
     *
     * @return 2018-02-07 16:26
     */
    public static String getCurrentTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String dateStr = sdf.format(now.getTimeInMillis());
        System.out.println("currentTime = " + dateStr);
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

    public static void main(String[] args) {
        int i = daysOfTwo("2019-08-19", getCurrentTime().split(" ")[0]) + 1;
        int i2 = daysOfTwo("2019-10-08", getCurrentTime().split(" ")[0]) + 24;
        System.out.println(i);
        System.out.println(i2);
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

    /**
     * 返回两个日期之间相差的月数(结束日期-开始日期)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int mothOfTwo(String date1, String date2) {
        int result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
        return Math.abs(result + month);
    }

    /**
     * 返回两个时间之间的间隔 (00:00 ～ 12:00),必须是英文的:符号
     * 单位：分钟
     * 遵循   开始时间 ～ 结束时间
     */
    public static int minOfTwo(String time0, String time1) {
        int hour0 = Integer.parseInt(time0.split(":")[0]);
        int min0 = Integer.parseInt(time0.split(":")[1]);
        int hour1 = Integer.parseInt(time1.split(":")[0]);
        int min1 = Integer.parseInt(time1.split(":")[1]);
        int i0 = Integer.parseInt(time0.split(":")[0] + time0.split(":")[1]);
        int i1 = Integer.parseInt(time1.split(":")[0] + time1.split(":")[1]);
        if (i0 < i1) {
            //没有跨天
            return (hour1 * 60 + min1) - (hour0 * 60 + min0);
        } else {//跨天
            int m0 = hour0 * 60 + min0;
            int m1 = hour1 * 60 + min1;
            return 24 * 60 - m0 + m1;
        }
    }

    /**
     * 两个时间相差多少分钟 ，开始时间 ～ 结束时间
     *
     * @param time0 00:00
     * @param time1 12:00
     * @return 结束时间 - 开始时间  单位（分钟）
     */
    public static int minInterval(String time0, String time1) {
        int hour0 = Integer.parseInt(time0.split(":")[0]);
        int min0 = Integer.parseInt(time0.split(":")[1]);
        int hour1 = Integer.parseInt(time1.split(":")[0]);
        int min1 = Integer.parseInt(time1.split(":")[1]);

        int intervalMin = hour1 * 60 + min1 - (hour0 * 60 + min0);

        LogUtil.e("DateUtil", "开始时间：" + time0 + "\t" + "结束时间：" + time1);
        LogUtil.e("DateUtil", "开始时间分钟：" + (hour0 * 60 + min0) + "\t" + "结束时间分钟：" + (hour1 * 60 + min1));
        LogUtil.e("DateUtil", "相差分钟：" + intervalMin);

        return intervalMin;

    }

    /**
     * 仅仅针对任务管理中的发布任务而做  返回以重复间隔为划分的时间字符串
     */
    public static String formatTime(String time, float timeInterval) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int min = Integer.parseInt(time.split(":")[1]);
        int newHour = (int) (hour + timeInterval);
        if (newHour >= 24) {
            newHour = newHour - 24;
        }
        String minStr = String.valueOf(min);
        if (minStr.length() == 1) {
            minStr = "0" + minStr;
        }
        String newHourStr = String.valueOf(newHour);
        return newHourStr.length() == 1 ? "0" + newHourStr + ":" + minStr : newHourStr + ":" + minStr;
    }

    /**
     * 是否这两个时间是隔天  12:00 - 12:00、12:00 - 11:00
     * 11:00 - 12:00不是
     */
    public static boolean isApartDay(String startTime, String endTime) {
        int start = Integer.parseInt(startTime.split(":")[0]);
        int end = Integer.parseInt(endTime.split(":")[0]);
        return end - start < 0;
    }

    /**
     * 0 ～ 24 小时返回 00 ～ 24
     *
     * @param time 形如 18:04
     * @return
     */
    public static String hour(String time) {
        String hourStr = time.split(":")[0];
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        return hourStr;
    }

    /**
     * 同上
     *
     * @param time
     * @return
     */
    public static String min(String time) {
        String minStr = time.split(":")[1];
        if (minStr.length() == 1) {
            minStr = "0" + minStr;
        }
        return minStr;
    }

    /**
     * 获取未来的时间
     *
     * @param assignMin 约定是距离现在多少分钟后的未来时间，e.g:30，表示30分钟后
     * @return 形如：2018-02-07 16:41
     */
    public static String getFutureTime(int assignMin) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, assignMin);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(now.getTimeInMillis());
    }

    /**
     * 获取指定（年月日时分）多少分钟后的时间
     *
     * @param startDate      2018-01-01
     * @param startTime      "08:00" 形式的时间,表示的肯定是当前天的时间
     * @param assignDuration 约定是距离 statTime 多少分钟后的未来时间，e.g:30，表示30分钟后
     * @return 形如：2018-02-07 16:41
     */
    public static String[] getFutureTime(String startDate, String startTime, int assignDuration, int calendarType) {
        String[] dates = startDate.split("-");
        String[] ts = startTime.split(":");

        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        now.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
        now.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
        now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ts[0]));
        now.set(Calendar.MINUTE, Integer.parseInt(ts[1]));

        // Calendar.MONTH
        // Calendar.DAY_OF_MONTH
        // Calendar.HOUR_OF_DAY
        // Calendar.MINUTE
        now.add(calendarType, assignDuration);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(now.getTimeInMillis()).split(DATE_TIME_SPLIT);
    }

    /**
     * 将Calendar显示的月份+1之后返回正常的月份
     *
     * @param month Calendar返回的月份
     * @return
     */
    public static String transformMonth(int month) {
        switch (month) {
            case 0:
                return "01";
            case 1:
                return "02";
            case 2:
                return "03";
            case 3:
                return "04";
            case 4:
                return "05";
            case 5:
                return "06";
            case 6:
                return "07";
            case 7:
                return "08";
            case 8:
                return "09";
            case 9:
                return "10";
            case 10:
                return "11";
            case 11:
                return "12";
            default:
                return "-1";
        }
    }
}
