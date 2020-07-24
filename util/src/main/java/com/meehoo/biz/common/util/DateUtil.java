package com.meehoo.biz.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final long DayLong = 1000*60*60*24;

    public static List<Date> getEveryDayOfMonth(){
        Date now = getTodayBegin();
        int total = get(now, Calendar.DAY_OF_MONTH);
        List<Date> result = new ArrayList<>(total);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);


        for (int n=1;n<=total;n++){
            calendar.set(Calendar.DAY_OF_MONTH,n);
            result.add(calendar.getTime());
        }
        return result;
    }

    public static String timeToString(Date date){
        if (date!=null)
            return sdf1.format(date);
        return "";
    }

    public static String timeToNumber(Date date){
        if (date!=null)
            return sdf.format(date);
        return "";
    }

    public static String dateToString(Date date){
        if (date != null)
            return sdf2.format(date);
        return "";
    }

    public static String longToString(long currentTime){
        String time = sdf.format( Long.valueOf(currentTime));
        return time;
    }

    public static Date toDate(Object object) throws Exception{
        return null;
    }

    public static Date stringToDate(String string) throws Exception{
        if (string.length() == 19){
            return sdf1.parse(string);
        }else if (string.length() == 10){
            return sdf2.parse(string);
        }else if (string.length() == 14){
            return sdf.parse(string);
        }else{
            throw new RuntimeException("时间长度不对");
        }
    }

    public static Date getTodayBegin(){
        return getDateDayBegin(new Date());
    }

    public static Date getDateDayBegin(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date getThisMonthBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date getThisYearBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static int countYear(Date date){
        Date now = new Date();
        return (int)((now.getTime() - date.getTime())/(long)(1000*60*60*24*365));
    }

    public static int countDay(Date date){
        Date now = new Date();
        return (int)((now.getTime() - date.getTime())/(1000*60*60*24));
    }

    public static Date before(int yearQty){
        Calendar calendar = Calendar.getInstance();
        int n = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR,n-yearQty);
        return calendar.getTime();
    }

    public static Date before(int qty,int type){
        Calendar calendar = Calendar.getInstance();
        int n = calendar.get(type);
        calendar.set(type,n-qty);
        return calendar.getTime();
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int get(Date date,int type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int result = calendar.get(type);
        if (type == Calendar.MONTH){
            result++;
        }
        return result;
    }

    // 获得当月天数
    public static int getDayQtyOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
