package com.joshgm3z.chatdaemon.common.utils;

import java.util.Calendar;

public class DateUtil {

    public static String getPrettyTime(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int amPm = calendar.get(Calendar.AM_PM);

        String dateTime
                = hour + ":"
                + minute + " "
                + (amPm == 0 ? "am" : "pm") + ", "
                + getWeekDay(weekDay) + ", "
                + getMonth(month) + " "
                + day;
        return dateTime;
    }

    private static String getWeekDay(int day) {
        String weekDay = "";
        switch (day) {
            case 1:
                weekDay = "Sun";
                break;

            case 2:
                weekDay = "Mon";
                break;

            case 3:
                weekDay = "Tue";
                break;

            case 4:
                weekDay = "Wed";
                break;

            case 5:
                weekDay = "Thu";
                break;

            case 6:
                weekDay = "Fri";
                break;

            case 7:
                weekDay = "Sat";
                break;
        }
        return weekDay;
    }

    private static String getMonth(int month) {
        String monthText = "";
        switch (month) {
            case 1:
                monthText = "Jan";
                break;

            case 2:
                monthText = "Feb";
                break;

            case 3:
                monthText = "Mar";
                break;

            case 4:
                monthText = "Apr";
                break;

            case 5:
                monthText = "May";
                break;

            case 6:
                monthText = "Jun";
                break;

            case 7:
                monthText = "Jul";
                break;

            case 8:
                monthText = "Aug";
                break;

            case 9:
                monthText = "Sep";
                break;

            case 10:
                monthText = "Oct";
                break;

            case 11:
                monthText = "Nov";
                break;

            case 12:
                monthText = "Dec";
                break;
        }
        return monthText;
    }

}
