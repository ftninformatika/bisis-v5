package com.ftninformatika.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

  public static String toGMTstring(Date date) {
    DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z");
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    return df.format(date);
  }
}
