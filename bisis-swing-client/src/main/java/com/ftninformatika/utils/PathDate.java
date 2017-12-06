package com.ftninformatika.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dboberic on 06/12/2017.
 */
public class PathDate {
    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
    };

    private final Date date;

    public PathDate(Date date) {
        this.date = date;
    }

    @Override public String toString() {
        return DF.get().format(date);
    }
}