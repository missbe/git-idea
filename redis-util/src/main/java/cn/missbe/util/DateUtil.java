package cn.missbe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午2:46
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class DateUtil {
    private static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
    private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat MM_dd = new SimpleDateFormat("MM月dd号");
    private static final SimpleDateFormat HH_mm = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat yyyy_MM_ddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Object lock = new Object();

    /**
     * 获取当前时间的毫秒数
     * @return 返回毫秒数
     */
    public static long nowTimeInMillis(){
        String dateStr = yyyyMMddHHmmss.format(new Date());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyyMMddHHMMSS").parse(dateStr));
        } catch (ParseException e) {
            PrintUtil.print("当前时间解析毫秒数异常.");
        }
        return calendar.getTimeInMillis();
    }
    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:201808
     */
    public static String formateDateyyyyMM(Date date) {
        synchronized (lock) {
            return yyyyMM.format(date);
        }
    }
    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:20180811
     */
    public static String formateDateyyyyMMdd(Date date) {
        synchronized (lock) {
            return yyyyMMdd.format(date);
        }
    }
    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:2018-08-11 11:37:39
     */
    public static String formateDateyyyy_MM_ddHHmmss(Date date) {
        synchronized (lock) {
            return yyyy_MM_ddHHmmss.format(date);
        }
    }
    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:20180811113954
     */
    public static String formateDateyyyyMMddHHmmss(Date date) {
        synchronized (lock) {
            return yyyyMMddHHmmss.format(date);
        }
    }
    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 如：11:36
     */
    public static String formateDateHH_mm(Date date) {
        synchronized (lock) {
            return HH_mm.format(date);
        }
    }

    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:2018-08-11
     */
    public static String formateDateyyyy_MM_dd(Date date) {
        synchronized (lock) {
            return yyyy_MM_dd.format(date);
        }
    }

    /**
     * 格式化日期
     * @param date 要格式化的时期
     * @return 返回格式化的字符串 eg:08月11号
     **/
    public static String formateDate_MM_dd(Date date) {
        synchronized (lock) {
            return MM_dd.format(date);
        }
    }

    /**
     * @param dateStr 时间串
     * @return 转化结果，无法转化则new date
     */
    public static Date parseDate(String dateStr) {
        synchronized (lock) {
            try {
                return yyyy_MM_ddHHmmss.parse(dateStr);
            } catch (Exception e) {
                try {
                    return yyyy_MM_dd.parse(dateStr);
                } catch (Exception e2) {
                    try {
                        return yyyyMMdd.parse(dateStr);
                    } catch (Exception e3) {
                        PrintUtil.print("can't parse to Date :" + dateStr, SystemLog.Level.error);
                        return new Date();
                    }
                }
            }
        }
    }

    /**
     * 获取下一天时间串
     * @param dateStr eg:20180811114449
     * @return 下一天的时间串
     */
    public static String getNextDayString(String dateStr) {
        Date date;
        synchronized (lock) {
            try {
                date = yyyy_MM_ddHHmmss.parse(dateStr);
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                return formateDateyyyy_MM_ddHHmmss(date);
            } catch (Exception e) {
                try {
                    date = yyyy_MM_dd.parse(dateStr);
                    date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                    return formateDateyyyy_MM_dd(date);
                } catch (Exception e2) {
                    try {
                        date = yyyyMMdd.parse(dateStr);
                        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                        return formateDateyyyyMMdd(date);
                    } catch (Exception e3) {
                        PrintUtil.print("cant getNextDayString :" + dateStr, SystemLog.Level.error);
                        return "";
                    }
                }
            }
        }
    }

    /**
     * 获取随机日期
     * @param beginDate 起始日期，格式为：yyyyMMdd
     * @param endDate   结束日期，格式为：yyyyMMdd
     * @return
     */
    public static Date randomDate(String beginDate, String endDate) {
        try {
            Date start = parseDate(beginDate);
            Date end = parseDate(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间 串
     * @param bdate  较大的时间 串
     * @return 相差天数
     * @throws
     */
    public static int daysBetween(String smdate, String bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(parseDate(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * 分 * 秒 * 毫秒 后的格式
     */
    public static String formatDuring(long mss) {
        long minutes = (mss) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        long ms = (mss % (1000));
        if (minutes != 0) {
            return minutes + "分钟" + (seconds != 0 ? seconds + "秒" + (ms != 0 ? ms + "毫秒" : "") : "");
        }
        if (seconds != 0) {
            return seconds + "秒" + (ms != 0 ? ms + "毫秒" : "");
        }
        return ms + "毫秒";
    }

    public static Date beginOfToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date beginOfWeek() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        date.setTime(date.getTime() - 3600000 * 24);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date beginOfMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当前日期是周几<br>
     *
     * @param dt
     * @return 当前日期是周几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 返回当前时间是一周的第几天
     * @param date 给定时间
     * @return 返回字符串形式的第几天
     **/
    public static String getWeekIntStrOfDate(Date date) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
