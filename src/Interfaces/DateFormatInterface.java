package Interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Functional Interface class for using lambda to set the SimpleDateFormat pattern
 * @author Steve Corwin Amalfitano
 */
public interface DateFormatInterface {

    /**
     * Method for setting the SimpleDateFormat pattern
     */
    SimpleDateFormat dateFormat(String pattern);
}

