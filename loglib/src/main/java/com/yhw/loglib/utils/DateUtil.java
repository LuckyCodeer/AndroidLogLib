package com.yhw.loglib.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.data;

/**
 * 日期工具
 * Author: yhw on 2017-11-10.
 */

public class DateUtil {

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";
    public static final String SIMPLE_FORMAT_1 = "MM-dd";
    public static final String SIMPLE_FORMAT_2 = "HH:mm:ss.sss";

    /**
     * 获取当前日期
     */
    public static String getDate(){
        return getDate(SIMPLE_FORMAT);
    }

    public static String getDate(String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    public static Date getDateBefore(int day){
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-day);
        return calendar.getTime();
    }
}
