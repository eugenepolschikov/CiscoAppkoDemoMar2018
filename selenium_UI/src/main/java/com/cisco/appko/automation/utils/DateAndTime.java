package com.cisco.appko.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime {
    private static final Logger log = LoggerFactory.getLogger(DateAndTime.class);

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return (dateFormat.format(date).toString());
    }

    public static String timeStampNowYYMMDDHHSS() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        Date date = new Date();
        return (dateFormat.format(date).toString());
    }

    public static String theMonthString(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static String theMonthCalendarPopup(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }

    public static String getMonthAsStringForCalendarPopup() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return theMonthCalendarPopup(localDate.getMonthValue() - 1);
    }

    public static String getMonthAsStringAndYYYY() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (theMonthString(localDate.getMonthValue() - 1) + " " + String.valueOf(localDate.getYear()));
    }

    public static String getCurrentYear() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return String.valueOf(localDate.getYear());
    }

    public static String getTomorrowDay() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        return String.valueOf(c.get(Calendar.DAY_OF_MONTH));

    }


}
