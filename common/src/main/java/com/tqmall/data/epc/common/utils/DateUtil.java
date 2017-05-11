package com.tqmall.data.epc.common.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huangzhangting on 15/9/2.
 */
public class DateUtil {
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyMMdd = "yyMMdd";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static String dateToString(Date date, String dateFormat){
        DateTime dt = new DateTime(date);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);

        return dt.toString(formatter);
    }

    public static Date stringToDate(String str, String dateFormat){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
        DateTime dt = DateTime.parse(str, formatter);

        return dt.toDate();
    }

    public static long getTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当天时间的前d天（-d）或者后d天的时间
     *
     * @param cal     基准时间
     * @param d       加减天数
     * @param pattern 格式化的日期格式
     * @return 满足格式的日期String
     */
    public static String getFewDaysAgoOrAfterDate(Calendar cal, Integer d, String pattern) {
        Calendar calendar = (Calendar) cal.clone();
        calendar.add(Calendar.DATE, d);
        Date date = new Date(calendar.getTimeInMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
