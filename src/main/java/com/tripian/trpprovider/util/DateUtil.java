package com.tripian.trpprovider.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by semihozkoroglu on 10/05/2018.
 */

public class DateUtil {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    public static int getHourDiff(long timeStamp) {
        // Create copies so we don't update the original calendars.
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(timeStamp);

        Calendar end = Calendar.getInstance();

        return (int) TimeUnit.MILLISECONDS.toHours(end.getTimeInMillis() - start.getTimeInMillis());
    }

    public static long getDiffDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = sdf.parse(dateString);

                Calendar blockedDate = Calendar.getInstance();
                blockedDate.setTime(date);
                blockedDate.set(Calendar.HOUR_OF_DAY, 0);
                blockedDate.set(Calendar.MINUTE, 0);
                blockedDate.set(Calendar.SECOND, 0);
                blockedDate.set(Calendar.MILLISECOND, 0);

                Calendar today = Calendar.getInstance();
                today.add(Calendar.DAY_OF_YEAR, -7);
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);

                long diff = blockedDate.getTime().getTime() - today.getTime().getTime();

                if (diff > 0) {
                    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
                } else {
                    return 0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static int getCompareDates(Date startDate, Date endDate) {
        // Create copies so we don't update the original calendars.
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        return getCompareDates(start, end);
    }

    public static int getCompareDates(Calendar startCal, Calendar endCal) {
        // Create copies so we don't update the original calendars.
        Calendar start = Calendar.getInstance();
        start.setTimeZone(startCal.getTimeZone());
        start.setTimeInMillis(startCal.getTimeInMillis());

        Calendar end = Calendar.getInstance();
        end.setTimeZone(endCal.getTimeZone());
        end.setTimeInMillis(endCal.getTimeInMillis());

        // Set the copies to be at midnight, but keep the day information.

        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        // At this point, each calendar is set to midnight on
        // their respective days. Now use TimeUnit.MILLISECONDS to
        // compute the number of full days between the two of them.
        return (int) TimeUnit.MILLISECONDS.toDays(end.getTimeInMillis() - start.getTimeInMillis());
    }

    public static String getFormattedDateEvent(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        newFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return newFormat.format(new Date(dateTime));
    }
}
