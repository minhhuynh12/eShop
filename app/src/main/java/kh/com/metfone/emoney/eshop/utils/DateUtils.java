package kh.com.metfone.emoney.eshop.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DCV on 3/15/2018.
 */

public class DateUtils {
    public static final String PATTERN_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
    public static final String PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String PATTERN_YYYY_MM_dd = "yyyy-MM-dd";

    public static long timeInMs(long updatedAt) {
        return updatedAt * 1000L;
    }

    public static String formatDateTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
    }

    public static String getDuration(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat(PATTERN_DD_MM_YYYY_HH_MM_SS);
        try {
            Date startDate = df.parse(startTime);
            Date endDate = df.parse(endTime);
            long duration = endDate.getTime() - startDate.getTime();
            return String.valueOf(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        if (TextUtils.isEmpty(inputDate)) {
            return "";
        }
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
        }

        return outputDate;

    }
}
