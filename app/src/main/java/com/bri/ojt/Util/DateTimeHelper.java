package com.bri.ojt.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {

    public static String getTimestamp() {
        // Input
        Date date = new Date(System.currentTimeMillis());

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return sdf.format(date);
    }

    public static int getCurrentYear() {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return Integer.parseInt(sdf.format(date));
    }

    public static List<String> getLastThreeYear() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(getCurrentYear()));
        list.add(String.valueOf(getCurrentYear()-1));
        list.add(String.valueOf(getCurrentYear()-2));
        list.add(String.valueOf(getCurrentYear()-3));
        return list;
    }

    public static String getCurrentMonth() {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("id"));
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return sdf.format(date);
    }

    public static String getMonth(String month) {
        Locale locale = new Locale("in", "ID");
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", locale);
        Date date;
        String formatted = null;
        try {
            date = sdf.parse(month);
            sdf = new SimpleDateFormat("MM", locale);
            formatted = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatted;
    }


    /**
     * Get last day.
     *
     * @param monthYear the month year
     * @return the last day
     */
    public static int getLastDay(String monthYear) {
        DateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy", new Locale("in", "ID"));
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(monthYear);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
