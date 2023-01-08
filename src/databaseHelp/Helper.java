package databaseHelp;

import Interfaces.DateFormatInterface;
import javafx.scene.control.ComboBox;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper function class to be used by other methods throughout application
 * @author Steve Corwin Amalfitano
 */
public class Helper {

    /**
     * lambda expression to set the pattern of a new SimpleDateFormat. Used to clean up repeated code where the only
     * thing that changes is the pattern.
     */
    static DateFormatInterface formatPattern = (String pattern) -> new SimpleDateFormat(pattern);

    /**
     * Converts a local time string to UTC time
     * @param time the local time
     * @return the converted UTC time string
     */
    public static String localToUTC(String time) {
        SimpleDateFormat localFormat = formatPattern.dateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        Date localTime = null;


        SimpleDateFormat utcFormat = formatPattern.dateFormat("yyyy-MM-dd HH:mm:ss");
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

    /**
     * Converts a UTC time string to local time
     * @param currentTime the UTC time
     * @return the converted local time string
     */
    public static String utcToLocal(String currentTime) {
        SimpleDateFormat localFormat = formatPattern.dateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        Date utcTime = null;

        SimpleDateFormat utcFormat = formatPattern.dateFormat("yyyy-MM-dd HH:mm:ss");
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

    /**
     * Verifies Appt is within business hours
     * @param startTime the starting time string (HH:mm:ss) for the Appointment
     * @param endTime the ending time string (HH:mm:ss) for the Appointment
     * @return true if the appointment falls within business hours, other returns false
     */
    public static boolean verifyBusHours(String startTime, String endTime) {
        SimpleDateFormat localFormat = formatPattern.dateFormat("HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        Date localStartTime;
        Date localEndTime;
        Date estStartTimeDate;
        Date estEndTimeDate;

        SimpleDateFormat estFormat = formatPattern.dateFormat("HH:mm:ss");
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

    /**
     * Verifies user input time is in correct HH:mm:ss format
     * @param time the time to verify
     * @return true if correct format, otherwise return false.
     */
    public static boolean verifyTimeFormat(String time) {
        if (time.length() > 8) {
            return false;
        }
        try {
            DateFormat checkFormat = formatPattern.dateFormat("HH:mm:ss");
            checkFormat.setLenient(false);
            checkFormat.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Verifies user input date is in correct yyyy-MM-dd format
     * @param date the date to verify
     * @return true if correct format, otherwise return false.
     */
    public static boolean verifyDateFormat(String date) {
        if (date.length() > 10) {
            return false;
        }
        try {
            DateFormat checkFormat = formatPattern.dateFormat("yyyy-MM-dd");
            checkFormat.setLenient(false);
            checkFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Gets current UTC time in yyyy-MM-dd HH:mm:ss format
     * @return current UTC time
     */
    public static String getCurrentUtcTime() {
        SimpleDateFormat formatter = formatPattern.dateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return localToUTC(formatter.format(date));
    }

    /**
     * Checks to verify a selection has been made in all available comboboxes
     * @param comboArray the combobox checked for selection
     * @return true if all comboboxes have a selectionm, otherwise return false
     */
    public static boolean checkForSelect(ComboBox[] comboArray) {
        for (ComboBox c : comboArray) {
            if (c.getSelectionModel().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
