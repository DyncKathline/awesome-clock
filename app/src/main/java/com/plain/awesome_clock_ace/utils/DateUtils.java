package com.plain.awesome_clock_ace.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getHour() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        return dateFormat.format(date);
    }

}