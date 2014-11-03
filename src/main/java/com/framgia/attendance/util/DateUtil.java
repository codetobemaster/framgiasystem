package com.framgia.attendance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    public static enum DateUtilInterval {
        DAY, MONTH, YEAR
    }

    private DateUtil() {
        super();
    }

    /**
     * @param dateValue
     *            YYYYMMDD or YYYYMM
     * @return YYYYMMの場合、DDを01に
     * @throws ParseException
     */
    public static Date toDate(Integer dateValue) throws ParseException {
        String dateStr = String.valueOf(dateValue);
        if (dateStr.length() == 6) {
            dateStr += "01";
        }
        return new SimpleDateFormat("yyyyMMdd").parse(dateStr);
    }

    /**
     * @param date
     * @return 日付型のデータをYYYYMMDD形式の数値に変換
     */
    public static Integer toYyyymmddInteger(Date date) {
        if (null == date) {
            return null;
        }
        return new Integer(new SimpleDateFormat("yyyyMMdd").format(date));
    }

    /**
     * @param date
     * @return 日付型のデータをYYYYMM形式の数値に変換
     */
    public static Integer toYyyymmInteger(Date date) {
        if (null == date) {
            return null;
        }
        return new Integer(new SimpleDateFormat("yyyyMM").format(date));
    }

    /**
     * @param date
     * @return 日付型のデータをYYYY形式の数値に変換
     */
    public static Integer toYyyyInteger(Date date) {
        if (null == date) {
            return null;
        }
        return new Integer(new SimpleDateFormat("yyyy").format(date));
    }

    public static Date toDate(String dateStr) throws ParseException {
        return toDate(Integer.parseInt(dateStr));
    }

    public static Integer toInteger(Date date) {
        return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(date));
    }

    /**
     * 指定した月数分日付を加算する。
     * Ex:date=2012.03.01、months=-12の場合、2011.03.01を表す日付が返却される。
     * 
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, Integer months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);

        return calendar.getTime();
    }

    /**
     * yyyymm形式の日付から年を取得する
     * 
     * @param yearMonth
     * @return
     */
    public static int getYear(int yearMonth) {
        if (yearMonth == 0) {
            return 0;
        }
        return yearMonth / 100;
    }

    /**
     * yyyymm形式の日付から月を取得する
     * 
     * @param yearMonth
     * @return
     */
    public static int getMonth(int yearMonth) {
        return yearMonth - (getYear(yearMonth) * 100);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public static String toyyyyMMddHHmmssStr(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }
    
    public static String toyyyyMMddWithSlashStr(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }
    
    public static String dateToString(Date date, String formatDate) {
        if (date == null) return "";
        
        return new SimpleDateFormat(formatDate).format(date);
    }
}
