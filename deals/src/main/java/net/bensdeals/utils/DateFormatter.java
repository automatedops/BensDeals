package net.bensdeals.utils;

import android.content.Context;
import net.bensdeals.R;
import net.bensdeals.provider.CurrentDateProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.getInstance;

public class DateFormatter {
    public static final long SECOND_IN_MILLI = 1000;
    public static final long MINUTE_IN_MILLI = 60 * SECOND_IN_MILLI;
    public static final long HOUR_IN_MILLI = 60 * MINUTE_IN_MILLI;
    public static final long DAY_IN_MILLI = 24 * HOUR_IN_MILLI;

    public static Date stringToDate(String dateString) {
        try {
            //Tue, 03 Jul 2012 22:13:20 -0700
            return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZ").parse(dateString.trim());
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String formatDate(CurrentDateProvider dateProvider, Date date, Context context) {
        long now = dateProvider.get().getTime();
        long dateTime = date.getTime();
        long diff = now - dateTime;
        if(diff < 0) return "";
        if (diff < HOUR_IN_MILLI) {
            int minutes = (int) (diff / MINUTE_IN_MILLI);
            return context.getResources().getQuantityString(R.plurals.minutes_format, minutes, minutes);
        } else {
            if (isSameDay(now, dateTime)) {
                int hours = (int) (diff / HOUR_IN_MILLI);
                return context.getResources().getQuantityString(R.plurals.hours_format, hours, hours);
            } else {
                int days = (int) ((diff / DAY_IN_MILLI) + 1);
                return context.getResources().getQuantityString(R.plurals.days_format, days, days);
            }
        }
    }

    public static boolean isSameDay(long now, long timestamp) {
        long currentDay = getCurrentDayInMillis(getCalendar(now));
        long beforeTime = now - currentDay;
        long afterTime = now - currentDay + DAY_IN_MILLI;
        return betweenBeforeTimeAndAfterTime(beforeTime, afterTime, timestamp);
    }


    private static long getCurrentDayInMillis(Calendar calendar) {
        return calendar.get(HOUR_OF_DAY) * HOUR_IN_MILLI + calendar.get(MINUTE) * MINUTE_IN_MILLI + calendar.get(SECOND);
    }

    private static boolean betweenBeforeTimeAndAfterTime(long beforeTime, long afterTime, long timestamp) {
        Date time = new Date(timestamp);
        boolean before = new Date(beforeTime).before(time);
        boolean after = new Date(afterTime).after(time);
        return before && after;
    }

    private static Calendar getCalendar(long now) {
        Calendar calendar = getInstance();
        calendar.setTimeInMillis(now);
        return calendar;
    }
}
