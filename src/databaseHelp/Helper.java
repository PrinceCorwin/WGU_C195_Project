package databaseHelp;

import Interfaces.DateFormatInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Helper {

    static DateFormatInterface fullFormat = () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormatInterface yearFormat = () -> new SimpleDateFormat("yyyy-MM-dd");
    static DateFormatInterface timeFormat = () -> new SimpleDateFormat("HH:mm:ss");

    public static String localToUTC(String time) {
        SimpleDateFormat localFormat = fullFormat.dateFormat();
        localFormat.setTimeZone(TimeZone.getDefault());
        Date localTime = null;


        SimpleDateFormat utcFormat = fullFormat.dateFormat();
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String utcTime = "";
        try {
            localTime = localFormat.parse(time);
            utcTime = utcFormat.format(localTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utcTime;
    }
    public static String utcToLocal(String currentTime) {
        SimpleDateFormat localFormat = fullFormat.dateFormat();
        localFormat.setTimeZone(TimeZone.getDefault());
        Date utcTime = null;

        SimpleDateFormat utcFormat = fullFormat.dateFormat();
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String localTime = "";
        try {
            utcTime = utcFormat.parse(currentTime);
            localTime = localFormat.format(utcTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localTime;
    }

    public static boolean verifyBusHours(String startTime, String endTime) {
        SimpleDateFormat localFormat = timeFormat.dateFormat();
        localFormat.setTimeZone(TimeZone.getDefault());
        Date localStartTime;
        Date localEndTime;
        Date estStartTimeDate;
        Date estEndTimeDate;

        SimpleDateFormat estFormat = timeFormat.dateFormat();
        estFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        String estStartTime = "";
        String estEndTime = "";
        try {
            localStartTime = localFormat.parse(startTime);
            localEndTime = localFormat.parse(endTime);
            estStartTime = estFormat.format(localStartTime);
            estEndTime = estFormat.format(localEndTime);
            estStartTimeDate = estFormat.parse(estStartTime);
            estEndTimeDate = estFormat.parse(estEndTime);
            Date busStart = estFormat.parse("08:00:00");
            Date busEnd = estFormat.parse("22:00:00");
            if (estStartTimeDate.before(busStart) || estEndTimeDate.after(busEnd)) {
                System.out.println("outside business hours");
//                set error label
                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        System.out.println("inside business hours");
        return true;
    }
    public static boolean verifyTimeFormat(String time) {
        if (time.length() > 8) {
            return false;
        }
        try {
            DateFormat checkFormat = timeFormat.dateFormat();
            checkFormat.setLenient(false);
            checkFormat.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean verifyDateFormat(String date) {
        if (date.length() > 10) {
            return false;
        }
        try {
            DateFormat df = yearFormat.dateFormat();
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getCurrentUtcTime() {
        SimpleDateFormat formatter = fullFormat.dateFormat();
        Date date = new Date();
        return localToUTC(formatter.format(date));
    }
}
