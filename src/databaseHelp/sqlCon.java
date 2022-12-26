package databaseHelp;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class sqlCon {
    private static final String sqlProtocol = "jdbc";
    private static final String splVend = ":mysql:";
    private static final String sqlDbLoc = "//localhost/";
    private static final String sqlDbName = "client_schedule";
    private static final String sqlIp = sqlProtocol + splVend + sqlDbLoc + sqlDbName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String sqlDriver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String appUser = "sqlUser"; // Username
    public static Connection appConn;  // Connection Interface

    public static void openConnection()
    {
        try {
            Class.forName(sqlDriver); // Locate Driver
            // Password
            String password = "Passw0rd!";
            appConn = DriverManager.getConnection(sqlIp, appUser, password); // Reference Connection object
            System.out.println("You are connected");
        }
        catch(Exception e)
        {
//            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return appConn;
    }

    public static void closeConnection() {
        try {
            appConn.close();
            System.out.println("No longer connected");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
