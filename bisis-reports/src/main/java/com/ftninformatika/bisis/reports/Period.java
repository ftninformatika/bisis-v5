package com.ftninformatika.bisis.reports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Oznacava vremenski period izmedju dva datuma.
 * 
 * Moze da se inicijalizuje na osnovu stringa koji ima format
 * yyyyMMdd-yyyyMMdd ili pomocu sledecih kljucnih reci:
 * currentyear
 * lastyear
 * currentmonth
 * lastmonth
 * currentquarter
 * lastquarter
 * currenthalf
 * lasthalf
 * quarter1
 * quarter2
 * quarter3
 * quarter4
 * half1
 * half2
 */
public class Period {
  
  public Period() {
  }
  public Period(Date startDate, Date endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }
  public Period(String s) {
    parse(s);
  }
  
  public Date getStartDate() {
    return startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public boolean isInPeriod(Date date) {
    if (date == null)
      return false; 
    return !(date.after(endDate) || date.before(startDate));
  }
  
  public String toString() {
    return sdf1.format(startDate) + "-" + sdf1.format(endDate);
  }

  private void parse(String s) {
    s = s.trim();
    GregorianCalendar cal = new GregorianCalendar();

    int thisyear = cal.get(Calendar.YEAR);
    int lastyear = thisyear - 1;
    
    int thismonth = cal.get(Calendar.MONTH);
    int lastmonth = thismonth == Calendar.JANUARY ? Calendar.DECEMBER : thismonth - 1; 
    
    if ("currentyear".equals(s)) {
      init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.DECEMBER, 31);
    } else if ("lastyear".equals(s)) {
      init(lastyear, Calendar.JANUARY, 1, lastyear, Calendar.DECEMBER, 31);
    
    } else if ("currentmonth".equals(s)) {
      init(thisyear, thismonth, 1, thisyear, thismonth, lastDayOfMonth(cal, thisyear, thismonth));
    } else if ("lastmonth".equals(s)) {
      if (lastmonth != Calendar.DECEMBER)
        init(thisyear, lastmonth, 1, thisyear, lastmonth, lastDayOfMonth(cal, thisyear, lastmonth));
      else
        init(lastyear, lastmonth, 1, lastyear, lastmonth, lastDayOfMonth(cal, lastyear, lastmonth));
    } else if ("currentquarter".equals(s)) {
      if (thismonth < Calendar.APRIL)
        init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.MARCH, 31);
      else if (thismonth < Calendar.JULY)
        init(thisyear, Calendar.APRIL, 1, thisyear, Calendar.JUNE, 30);
      else if (thismonth < Calendar.OCTOBER)
        init(thisyear, Calendar.JULY, 1, thisyear, Calendar.SEPTEMBER, 30);
      else
        init(thisyear, Calendar.OCTOBER, 1, thisyear, Calendar.DECEMBER, 31);
    } else if ("lastquarter".equals(s)) {
      if (thismonth < Calendar.APRIL)
        init(lastyear, Calendar.OCTOBER, 1, thisyear, Calendar.DECEMBER, 31);
      else if (thismonth < Calendar.JULY)
        init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.MARCH, 31);
      else if (thismonth < Calendar.OCTOBER)
        init(thisyear, Calendar.APRIL, 1, thisyear, Calendar.JUNE, 30);
      else
        init(thisyear, Calendar.JULY, 1, thisyear, Calendar.SEPTEMBER, 30);
    } else if ("currenthalf".equals(s)) {
      if (thismonth < Calendar.JULY)
        init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.JUNE, 30);
      else
        init(thisyear, Calendar.JULY, 1, thisyear, Calendar.DECEMBER, 31);
    } else if ("lasthalf".equals(s)) {
      if (thismonth < Calendar.JULY)
        init(lastyear, Calendar.JULY, 1, lastyear, Calendar.DECEMBER, 31);
      else
        init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.JUNE, 30);
    } else if ("quarter1".equals(s)) {
      init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.MARCH, 31);
    } else if ("quarter2".equals(s)) {
      init(thisyear, Calendar.APRIL, 1, thisyear, Calendar.JUNE, 30);
    } else if ("quarter3".equals(s)) {
      init(thisyear, Calendar.JULY, 1, thisyear, Calendar.SEPTEMBER, 30);
    } else if ("quarter4".equals(s)) {
      init(thisyear, Calendar.OCTOBER, 1, thisyear, Calendar.DECEMBER, 31);
    } else if ("half1".equals(s)) {
      init(thisyear, Calendar.JANUARY, 1, thisyear, Calendar.JUNE, 30);
    } else if ("half2".equals(s)) {
      init(thisyear, Calendar.JULY, 1, thisyear, Calendar.DECEMBER, 31);
    } else if ("lasthalf1".equals(s)) {
      init(lastyear, Calendar.JANUARY, 1, lastyear, Calendar.JUNE, 30);
    } else if ("lasthalf2".equals(s)) {
      init(lastyear, Calendar.JULY, 1, lastyear, Calendar.DECEMBER, 31);
    }
    else {
      parseRange(s);
    }
    
    if (startDate.after(endDate)) {
      startDate = null;
      endDate = null;
      log.fatal("Invalid interval: " + sdf.format(startDate) + "-" + sdf.format(endDate));
    }
  }
  
  private void parseRange(String s) {
    Pattern pattern = Pattern.compile("\\d{8}-\\d{8}");
    Matcher matcher = pattern.matcher(s);
    if (!matcher.matches()) {
      startDate = endDate = null;
      log.fatal("Invalid date range: " + s);
      return;
    }
    try {
      startDate = sdf.parse(s.substring(0, 8));
      endDate = sdf.parse(s.substring(9));
    } catch (Exception ex) {
      startDate = null;
      endDate = null;
      log.fatal("Invalid date range: " + s);
    }
  }
  
  private int lastDayOfMonth(GregorianCalendar cal, int year, int month) {
    switch (month) {
      case Calendar.JANUARY:
      case Calendar.MARCH:
      case Calendar.MAY:
      case Calendar.JULY:
      case Calendar.AUGUST:
      case Calendar.OCTOBER:
      case Calendar.DECEMBER:
        return 31;
      case Calendar.APRIL:
      case Calendar.JUNE:
      case Calendar.SEPTEMBER:
      case Calendar.NOVEMBER:
        return 30;
      case Calendar.FEBRUARY:
        return cal.isLeapYear(year) ? 29 : 28;
      default:
        return -1;
    }
  } 
  
  private void init(int year1, int month1, int day1, int year2, int month2, int day2) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.set(year1, month1, day1);
    startDate = cal.getTime();
    cal.set(year2, month2, day2);
    endDate = cal.getTime();  
  }
  
  private Date startDate;
  private Date endDate;
  private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  private SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
  private static Log log = LogFactory.getLog(Period.class);
}
