/***
 *  @author Petar
 *  ***/
package com.ftninformatika.utils.date;

import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

  private static final String DATE_REG_EX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
  private static final long MAX_DATE_RANGE_DAYS = 367;

  public static String toGMTstring(Date date) {
    DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z");
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    return df.format(date);
  }

  public static Date getYearStartFromDate(Date date){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    calendar.set(year, 0, 0, 0, 0, 0);
    return calendar.getTime();
  }

  public static Date getStartOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DATE);
    calendar.set(year, month, day, 0, 0, 0);
    return calendar.getTime();
  }

  public static Date getEndOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DATE);
    calendar.set(year, month, day, 23, 59, 59);
    return calendar.getTime();
  }

  public static Date getNextDay(Date date){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  public static Date getYesterday(Date date){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, -1);
    return c.getTime();
  }

  public static boolean compareDates(Date d1, Date d2){
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(d1);
    cal2.setTime(d2);
    boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    return sameDay;
  }

  public static boolean inCircReportDateRange(Date d1, Date d2){
    long delta = d2.getTime() - d1.getTime();
    long days = TimeUnit.DAYS.convert(delta, TimeUnit.MILLISECONDS);
    return delta > 0 && days < MAX_DATE_RANGE_DAYS;
  }

  public static String getFormatedStringFromStringDate(String date){
    String retVal = "";
    if(date.matches(DATE_REG_EX)){
        String[] parts = date.split("-");
        retVal = parts[2] + "." + parts[1] + "." + parts[0];
    }
    return retVal;
  }
}
