package com.ftninformatika.util;

import java.util.Date;
import java.util.Calendar;

public class WorkCalendar {

    /**
     * Returns a date that is the given number of workdays away in the future from the given date.
     * @param date Starting date
     * @param amount Number of workdays in the future
     * @return A date that is the given number of workdays in the future from the given date
     */
    public static Date nextWorkDaysDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return nextWorkDaysDate(cal, amount);
    }

    /**
     * {@link #nextWorkDaysDate(Date, int)}
     */
    public static Calendar nextWorkDays(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return nextWorkDays(cal, amount);
    }

    /**
     * {@link #nextWorkDaysDate(Date, int)}
     */
    public static Date nextWorkDaysDate(Calendar cal, int amount) {
        return nextWorkDays(cal, amount).getTime();
    }

    /**
     * {@link #nextWorkDaysDate(Date, int)}
     */
    public static Calendar nextWorkDays(Calendar cal, int amount) {
        Calendar test = (Calendar)cal.clone();
        int workDayCount = 0;
        while (workDayCount < amount) {
            test.add(Calendar.DAY_OF_MONTH, 1);
            if (isWorkDay(test))
                workDayCount++;
        }
        return test;
    }

    /**
     * Returns whether two dates are the same, while ignoring time.
     */
    public static boolean isSameDate(Calendar cal1, Calendar cal2) {
        if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
            return false;
        if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
            return false;
        if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
            return false;
        return true;
    }

    /**
     * Returns whether two dates are the same, while ignoring time.
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDate(cal1, cal2);
    }

    /**
     * Work day calculation: a date is a non-work date if it is
     * (a) a Saturday,
     * (b) a Sunday,
     * (c) a state holiday,
     * (d) a Monday when a holiday is on previous Sunday, and it is not Easter Sunday.
     */
    public static boolean isWorkDay(int year, int month, int day) {
        Calendar theDate = Calendar.getInstance();
        theDate.set(year, month, day, 0, 0);
        Calendar dayBefore = (Calendar)theDate.clone();
        dayBefore.roll(Calendar.DAY_OF_MONTH, false);
        if (isHoliday(dayBefore) && theDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && !isSameDate(dayBefore, orthodoxEaster(year)))
            return false;
        if (isHoliday(theDate))
            return false;
        if (theDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return false;
        if (theDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        return true;
    }

    /**
     * {@link #isWorkDay(int, int, int)}
     */
    public static boolean isWorkDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWorkDay(cal);
    }

    /**
     * {@link #isWorkDay(int, int, int)}
     */
    public static boolean isWorkDay(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return isWorkDay(year, month, day);
    }

    /**
     * A calendar date is a holiday in Serbia if
     * (a) New Year: Jan 1st, Jan 2nd
     * (b) Orthodox Christmas: Jan 7th
     * (c) Statehood Day: Feb 15th, Feb 16th
     * (d) Orthodox Good Friday ({@link #orthodoxEaster(int)})
     * (e) Orthodox Easter ({@link #orthodoxEaster(int)})
     * (f) Labour Day: May 1st, May 2nd
     * (g) Armistice Day: Nov 11th
     */
    public static boolean isHoliday(int year, int month, int day) {
        // new year
        if (month == Calendar.JANUARY && day == 1)
            return true;
        if (month == Calendar.JANUARY && day == 2)
            return true;
        // orthodox christmas
        if (month == Calendar.JANUARY && day == 7)
            return true;
        // statehood day
        if (month == Calendar.FEBRUARY && day == 15)
            return true;
        if (month == Calendar.FEBRUARY && day == 16)
            return true;
        // orthodox easter
        Calendar easter = orthodoxEaster(year);
        Calendar goodFriday = (Calendar)easter.clone();
        goodFriday.add(Calendar.DAY_OF_MONTH, -2);
        if (month == easter.get(Calendar.MONTH) && day == easter.get(Calendar.DAY_OF_MONTH))
            return true;
        if (month == goodFriday.get(Calendar.MONTH) && day == goodFriday.get(Calendar.DAY_OF_MONTH))
            return true;
        // labour day
        if (month == Calendar.MAY && day == 1)
            return true;
        if (month == Calendar.MAY && day == 2)
            return true;
        // armistice day
        if (month == Calendar.NOVEMBER && day == 11)
            return true;
        return false;
    }

    /**
     * {@link #isHoliday(int, int, int)}
     */
    public static boolean isHoliday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isHoliday(cal);
    }

    /**
     * {@link #isHoliday(int, int, int)}
     */
    public static boolean isHoliday(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return isHoliday(year, month, day);
    }

    /**
     * Orthodox Easter day calculation using the Gauss method.
     * http://static.astronomija.org.rs/kalendar/knjiga/pravaslavniracunanje.htm
     */
    public static Calendar orthodoxEaster(int year) {
        int r1 = year % 19;
        int r2 = year % 4;
        int r3 = year % 7;
        int ra = 19 * r1 + 16;
        int r4 = ra % 30;
        int rb = 2 * r2 + 4 * r3 + 6 * r4;
        int r5 = rb % 7;
        int rc = r4 + r5;
        Calendar cal = Calendar.getInstance();
        cal.set(year, Calendar.APRIL, 3, 0, 0);
        cal.add(Calendar.DAY_OF_MONTH, rc);
        return cal;
    }

    /**
     * {@link #orthodoxEaster(int)}
     */
    public static Date orthodoxEasterDate(int year) {
        return orthodoxEaster(year).getTime();
    }

    /**
     * Create a java.util.Date from a year, month, day integers.
     */
    private static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0);
        return cal.getTime();
    }

    /**
     * Testing purposes only.
     */
    public static void main(String[] args) {
        // easter tests
        Date test = getDate(2019, Calendar.APRIL, 28);
        Date easter2019 = orthodoxEasterDate(2019);
        System.out.println("Easter 2019: " + easter2019 + "... " + (isSameDate(test, easter2019) ? "OK" : "ERROR"));
        test = getDate(2020, Calendar.APRIL, 19);
        Date easter2020 = orthodoxEasterDate(2020);
        System.out.println("Easter 2020: " + easter2020 + "... " + (isSameDate(test, easter2020) ? "OK" : "ERROR"));
        test = getDate(2021, Calendar.MAY, 2);
        Date easter2021 = orthodoxEasterDate(2021);
        System.out.println("Easter 2021: " + easter2021 + "... " + (isSameDate(test, easter2021) ? "OK" : "ERROR"));

        // workday tests
        test = getDate(2019, Calendar.JANUARY, 1);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2020, Calendar.JANUARY, 2);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2021, Calendar.JANUARY, 3);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2021, Calendar.JANUARY, 4);
        System.out.println("Is work day: " + test + "... " + (!isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2020, Calendar.FEBRUARY, 15);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2020, Calendar.FEBRUARY, 16);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2020, Calendar.FEBRUARY, 17);
        System.out.println("Is work day: " + test + "... " + (isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2021, Calendar.MAY, 3);
        System.out.println("Is work day: " + test + "... " + (!isWorkDay(test) ? "ERROR" : "OK"));
        test = getDate(2021, Calendar.MAY, 4);
        System.out.println("Is work day: " + test + "... " + (!isWorkDay(test) ? "ERROR" : "OK"));

        // next 3 workdays tests
        Date test1 = getDate(2020, Calendar.FEBRUARY, 15);
        Date test2 = getDate(2020, Calendar.FEBRUARY, 20);
        Date test3 = nextWorkDaysDate(test1, 3);
        System.out.println("Next 3 workdays from: " + test1 + "... " + (isSameDate(test2, test3) ? "OK" : "ERROR"));
        test1 = getDate(2020, Calendar.FEBRUARY, 16);
        test2 = getDate(2020, Calendar.FEBRUARY, 20);
        test3 = nextWorkDaysDate(test1, 3);
        System.out.println("Next 3 workdays from: " + test1 + "... " + (isSameDate(test2, test3) ? "OK" : "ERROR"));
        test1 = getDate(2020, Calendar.FEBRUARY, 17);
        test2 = getDate(2020, Calendar.FEBRUARY, 20);
        test3 = nextWorkDaysDate(test1, 3);
        System.out.println("Next 3 workdays from: " + test1 + "... " + (isSameDate(test2, test3) ? "OK" : "ERROR"));
        test1 = getDate(2020, Calendar.FEBRUARY, 18);
        test2 = getDate(2020, Calendar.FEBRUARY, 21);
        test3 = nextWorkDaysDate(test1, 3);
        System.out.println("Next 3 workdays from: " + test1 + "... " + (isSameDate(test2, test3) ? "OK" : "ERROR"));
        test1 = getDate(2020, Calendar.DECEMBER, 31);
        test2 = getDate(2021, Calendar.JANUARY, 6);
        test3 = nextWorkDaysDate(test1, 3);
        System.out.println("Next 3 workdays from: " + test1 + "... " + (isSameDate(test2, test3) ? "OK" : "ERROR"));
    }
}