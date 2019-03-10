package com.cloud.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cloud.util.RandomUtils;

/**
 * @author <a href='fz_du@people2000.net'>dufazuo</a>
 * @version 1.0
 * @Title：Development Framework
 * @Description：DateUtil 日期工具类，提供常用的对日期处理的静态方法
 * @Time 2014年9月26日 上午10:09:09 create
 */
public class DateUtil {
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static void main(String[] args) {
        //int ccc = DateUtil.getDiffDays(new Date(), new Date());
        System.err.println(formatDateTime(new Date()));
    }

    /**
     * 获取当前日期及时间
     *
     * @return 返回当前日期及时间
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取两个日期中较小的日期
     *
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return 返回较小的日期
     */
    public static Date getSmallDate(Date date1, Date date2) {
        return date1.compareTo(date2) < 0 ? date1 : date2;
    }

    /**
     * 获取两个日期中较大的日期
     *
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return 返回较大的日期
     */
    public static Date getBigDate(Date date1, Date date2) {
        return date1.compareTo(date2) > 0 ? date1 : date2;
    }

    /**
     * 在指定的日期上增加年数
     *
     * @param yearAmount
     *            年数
     * @param date
     *            指定日期
     * @return 返回增加月数后的日期
     */
    public static Date addYear2Date(int yearAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, yearAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 在指定的日期上增加月数
     *
     * @param monthAmount
     *            月数
     * @param date
     *            指定日期
     * @return 返回增加月数后的日期
     */
    public static Date addMonth2Date(int monthAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, monthAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 在指定的日期上增加天数
     *
     * @param dayAmount
     *            天数
     * @param date
     *            指定日期
     * @return 返回增加天数后的日期
     */
    public static Date addDay2Date(int dayAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, dayAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 在指定的日期上增加小时数
     *
     * @param hourAmount
     *            小时数
     * @param date
     *            指定日期
     * @return 返回增加小时数后的日期
     */
    public static Date addHour2Date(int hourAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, hourAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 在指定的日期上增加分钟数
     *
     * @param minuteAmount
     *            分钟数
     * @param date
     *            指定日期
     * @return 返回增加分钟数后的日期
     */
    public static Date addMinute2Date(int minuteAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, minuteAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param format
     *            时间表现形式，例如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @param date
     *            待格式化的日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(String format, Date date) {
        return formatDate(format, date, "");
    }

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param format
     *            时间表现形式，例如："yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @param date
     *            待格式化的日期
     * @param nullString
     *            空日期的替换字符，满足特殊需要
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(String format, Date date, String nullString) {
        String formatStr = nullString;

        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            formatStr = simpleDateFormat.format(date);
        }

        return formatStr;
    }

    /**
     * 将日期转换成"yyyy-MM-dd HH:mm:ss"格式的字符串
     *
     * @param date
     *            待格式化的日期
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateTime(Date date) {
        String formatStr = "";

        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatStr = simpleDateFormat.format(date);
        }

        return formatStr;
    }

    /**
     * 将字符串解析成年月日期类型，如果字符串含有/则按/分割,否则按-分割
     *
     * @param dateYMStr
     *            待解析的字符串
     * @return 返回解析后的日期
     */
    public static Date getDateYM(String dateYMStr) {
        Date date = null;
        try {
            if (dateYMStr != null) {
                String separator = dateYMStr.indexOf('/') > 0 ? "/" : "-";
                DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM");
                date = dateFormat.parse(dateYMStr);
            }
        } catch (ParseException parse) {
        }
        return date;
    }

    /**
     * 将字符串解析成年月日日期类型，如果字符串含有/则按/分割,否则按-分割
     *
     * @param dateStr
     *            待解析的字符串
     * @return 返回解析后的日期
     */
    public static Date getDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr != null) {
                String separator = dateStr.indexOf('/') > 0 ? "/" : "-";
                DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
                date = dateFormat.parse(dateStr);
            }
        } catch (ParseException parse) {
        }
        return date;
    }

    /**
     * 将字符串解析成日期类型，格式自定
     *
     * @param dateStr
     *            待解析的字符串
     * @return 返回解析后的日期
     */
    public static Date getDate(String dateStr, String formatStr) {
        Date date = null;
        try {
            if (dateStr != null) {
                DateFormat dateFormat = new SimpleDateFormat(formatStr);
                date = dateFormat.parse(dateStr);
            }
        } catch (ParseException parse) {
        }
        return date;
    }

    /**
     * 将字符串解析成年月日时分秒日期时间类型，如果字符串含有/则按/分割,否则以-分
     *
     * @param dateTimeStr
     *            待解析的字符串
     * @return 返回解析后的日期
     */
    public static Date getDateTime(String dateTimeStr) {
        Date date = null;
        try {
            String separator = dateTimeStr.indexOf('/') > 0 ? "/" : "-";
            DateFormat dateFormat = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd HH:mm:ss");
            date = dateFormat.parse(dateTimeStr);
        } catch (ParseException parse) {
        }
        return date;
    }

    /**
     * 获取指定日期是周几
     * 
     * @param date
     *            参数为null时表示获取当前日期是周几
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 获取指定日期是星期几
     * 
     * @param date
     *            参数为null时表示获取当前日期是星期几
     * @return
     */
    public static String getWeekOfDateTime(Date date) {
        String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 判断日期是否是周末
     * 
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0 || w == 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取传入日期的月份-日
     *
     * @param date
     *            待解析的日期
     * @return 月份-日
     */
    public static String dateFormat(Date date) {
        String resultCode = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        if (month < 10) {
            resultCode += "0" + month;
        } else {
            resultCode += month;
        }
        resultCode += "-";
        if (day < 10) {
            resultCode += "0" + day;
        } else {
            resultCode += day;
        }
        return resultCode;
    }

    /**
     * 获取传入日期的年份
     *
     * @param date
     *            待解析的日期
     * @return 返回该日期的年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取传入日期的月份
     *
     * @param date
     *            待解析的日期
     * @return 返回该日期的月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取传入日期月份的日
     *
     * @param date
     *            待解析的日期
     * @return 返回该日期的日
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获取传入日期月份的小时 12小时制
     *
     * @return 返回该日期的小时
     * @date 待解析的日期
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR);
    }

    /**
     * 获取传入日期月份的小时 24小时制
     *
     * @return 返回该日期的小时
     * @date 待解析的日期
     */
    public static int getHourOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取传入日期月份的分钟
     *
     * @return 返回该日期的分钟
     * @date 待解析的日期
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 两个日期的年份差
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的年份差，例1998-4-21~1999-6-21 相差1年2个月，返回1，
     */
    public static int getDiffYears(Date fromDate, Date toDate) {
        int diffMonths = getDiffMonths(fromDate, toDate);
        int diffYears = diffMonths / 12;
        return diffYears;
    }

    /**
     * 两个日期的月份差
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的月份差，例1998-4-21~1998-6-21 相差2个月，返回2
     */
    public static int getDiffMonths(Date fromDate, Date toDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        int fromYear = c.get(Calendar.YEAR);
        int fromMonth = c.get(Calendar.MONTH) + 1;
        c.setTime(toDate);
        int toYear = c.get(Calendar.YEAR);
        int toMonth = c.get(Calendar.MONTH) + 1;
        int monthCount;

        if (toYear == fromYear) {
            monthCount = toMonth - fromMonth;
        } else if (toYear - fromYear == 1) {
            monthCount = 12 - fromMonth + toMonth;
        } else {
            monthCount = 12 - fromMonth + 12 * (toYear - fromYear - 1) + toMonth;
        }
        return monthCount;
    }

    /**
     * 两个日期的天数差
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的天数差，例1998-4-21~1998-4-25 相差4天，返回4
     */
    public static int getDiffDays(Date fromDate, Date toDate) {
        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 两个日期的小时差，自定义精度和舍入方式
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的小时数
     */
    public static double getDiffHours(Date fromDate, Date toDate) {
        // return Tools.numberDivide(toDate.getTime() - fromDate.getTime(),
        // (1000 * 60 * 60 ), scale, roundType);
        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60));
    }

    /**
     * 两个日期的分钟差，自定义精度和舍入方式
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的小时数
     */
    public static double getDiffMinutes(Date fromDate, Date toDate) {
        // return Tools.numberDivide(toDate.getTime() - fromDate.getTime(),
        // (1000 * 60 * 60 ), scale, roundType);
        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60));
    }

    /**
     * 两个日期的秒数差
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return 返回两个日期的秒数差，例1998-4-21 10:00:00~1998-4-21 10:00:50 相差50秒，返回50
     */
    public static Long getDiffSeconds(Date fromDate, Date toDate) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(fromDate);
        fromCal.set(Calendar.MILLISECOND, 0);

        Calendar toCal = Calendar.getInstance();
        toCal.setTime(toDate);
        toCal.set(Calendar.MILLISECOND, 0);
        return (toCal.getTime().getTime() - fromCal.getTime().getTime()) / 1000;
    }

    /**
     * 获取一个星期中的第几天，周日算第一天
     *
     * @param date
     *            待解析的日期
     * @return 返回一个星期中的第几天
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取一个星期中的第几天，周一算第一天
     *
     * @param date
     *            待解析的日期
     * @return 返回一个星期中的第几天
     */
    public static int getChinaDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayOfWeek) {
            dayOfWeek = 8;
        }
        return dayOfWeek - 1;
    }

    /**
     * 获取一个月中的第几天，一个月中第一天的值为1
     *
     * @param date
     *            待解析的日期
     * @return 返回一个月中的第几天
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间的时间戳，精确到毫秒
     *
     * @return 返回当前时间的时间戳
     */
    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取某日的0时0分0秒的Date对象
     *
     * @param datetime
     *            待解析的日期
     * @return 传入日期的0时0分0秒的Date对象
     */
    public static Date getDayStart(Date datetime) {
        if (null == datetime) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据传入的日期获取当前日期的一月一日
     *
     * @param datetime
     * @return
     * @Time 2015年3月13日 上午11:24:54 create
     * @author xiehaibin
     */
    public static Date getFirstDayOfYear(Date datetime) {
        if (null == datetime) {
            return null;
        }
        int year = getYear(datetime);
        Calendar calendar = new GregorianCalendar(year, 0, 1);
        return calendar.getTime();
    }

    /**
     * 根据传入的日期获取当前日期的一日
     *
     * @param datetime
     * @return
     * @Time 2015年3月13日 上午11:24:54 create
     * @author xiehaibin
     */
    public static Date getFirstDayOfMonth(Date datetime) {
        if (null == datetime) {
            return null;
        }
        int year = getYear(datetime);
        int month = getMonth(datetime);
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        return calendar.getTime();
    }

    /**
     * 获取某日的23时59分59秒的Date对象
     *
     * @param datetime
     *            待解析的日期
     * @return Date 传入日期的23时59分59秒的Date对象
     */
    public static Date getDayEnd(Date datetime) {
        if (null == datetime) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Format data to string with specified style.
     *
     * @param dtmDate
     *            Date
     * @param num
     *            Default Style 3 1:yyyy.MM.dd 2:hh:mm:ss 3:yyyy.MM.dd
     *            HH:mm:ss(Default) 4:yyyy-MM-dd 5:yyyy 6:MM 7:dd 8:yyyy-MM-dd
     *            HH:mm 9:HH:mm 10:yyyy-MM-dd HH:mm:ss 11:yyyyMMdd
     * @return dateString String
     */
    public static String dateToStr(java.util.Date dtmDate, int num) {
        if (dtmDate == null) {
            return "";
        }
        String f = getDateFormet(num);
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(dtmDate);
    }

    public static Date strToDate(String dateString, int num) {
        if (dateString == null || dateString.length() == 0) {
            return null;
        }
        String formatstring = getDateFormet(num);
        Date parseDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();

        try {
            sdf.applyPattern(formatstring);
            parseDate = sdf.parse(dateString);
        } catch (ParseException e) {
            // ignore exception
            e.printStackTrace();
        }
        return parseDate;
    }

    private static String getDateFormet(int num) {
        String f;
        switch (num) {
        case 1:
            f = "yyyy.MM.dd";
            break;
        case 2:
            f = "kk:mm:ss";
            break;
        case 3:
            f = "yyyy.MM.dd kk:mm:ss";
            break;
        case 4:
            f = "yyyy-MM-dd";
            break;
        case 5:
            f = "yyyy";
            break;
        case 6:
            f = "MM";
            break;
        case 7:
            f = "dd";
            break;
        case 8:
            f = "yyyy-MM-dd HH:mm";
            break;
        case 9:
            f = "HH:mm";
            break;
        case 10:
            f = "yyyy-MM-dd HH:mm:ss";
            break;
        case 11:
            f = "yyyyMMdd";
            break;
        case 12:
            f = "yyyyMMddHHmmssSSS";
            break;
        case 13:
            f = "yyyyMM";
            break;
        case 14:
            f = "HH:mm:ss";
            break;
        case 15:
            f = "yyyy/MM/dd";
            break;
        case 16:
            f = "yyyyMMddHHmmss";
            break;
        case 17:
            f = "yyyy-MM-dd";
            break;
        default:
            f = "yyyy.MM.dd kk:mm:ss";
        }
        return f;
    }

    /**
     * Normal data format.
     *
     * @return String[]
     */
    private static String[] getdateformat() {
        return new String[] { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd" };
    }

    /**
     * Parse String to Date.
     *
     * @param dateString
     *            String of Date, the format is yyyy-MM-dd or yyyy/MM/dd or
     *            yyyy.MM.dd
     * @return Date
     * @throws ParseException
     */
    public static Date stringToDate(String dateString) throws ParseException {
        if (dateString == null || dateString.length() == 0) {
            return null;
        }
        String[] formatstring = getdateformat();
        int index = 0;
        Date parseDate = null;
        ParseException throwe = null;
        SimpleDateFormat sdf = new SimpleDateFormat();

        while (index < formatstring.length) {
            try {
                sdf.applyPattern(formatstring[index]);
                index++;
                parseDate = sdf.parse(dateString);
                break;
            } catch (ParseException gete) {
                throwe = gete;
                continue;
            }
        }
        if (parseDate == null)
            throw throwe;
        return parseDate;
    }

    public static String UUID() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatStr = simpleDateFormat.format(new Date());
        String result = formatStr + RandomUtils.number(6);
        return result;
    }

    /**
     * @日期：2019年1月10日
     * @作者：Sai.Du
     * @参数：已经格式化的字符串
     * @描述：需要再次格式化的字符串
     */
    public static String format(String info, int num) {
        if (info != null && info.length() != 0) {
            try {
                String formatstring = getDateFormet(num);
                SimpleDateFormat sdf = new SimpleDateFormat(formatstring);
                Date date = sdf.parse(info);
                String result = sdf.format(date);
                return result;
            } catch (ParseException var6) {
                System.out.println(var6.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
    
    
    public static Date addMinute(int num) {
    	Calendar nowTime = Calendar.getInstance();
    	nowTime.add(Calendar.MINUTE, num);
    	return nowTime.getTime();
    }
}
