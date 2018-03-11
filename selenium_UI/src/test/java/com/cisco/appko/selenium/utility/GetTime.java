package com.cisco.appko.selenium.utility;

import java.util.Calendar;
import java.util.Date;


public class GetTime {
    public static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
