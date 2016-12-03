package ro.code4.monitorizarevot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String calendarToString(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
        return formatter.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String string) {
        if (string == null) {
            return null;
        }
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            cal.setTime(sdf.parse(string));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
