package databaseHelp;

import classes.Appt;
import classes.Contact;
import classes.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
    public static ObservableList<Appt> getApptList() {
        ObservableList<Appt> allAppts = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from appointments";
            PreparedStatement myPs = sqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();

            while(myResult.next()) {
                int id = myResult.getInt("Appointment_ID");
                String title = myResult.getString("Title");
                String start = myResult.getString("Start");
                String desc = myResult.getString("Description");
                int contact = myResult.getInt("Contact_ID");
                String loc = myResult.getString("Location");
                String type = myResult.getString("Type");
                String end = myResult.getString("End");
                int custId = myResult.getInt("Customer_ID");
                int userId = myResult.getInt("User_ID");
                String createdBy = myResult.getString("Created_By");
                String created = myResult.getString("Create_Date");
                String update = myResult.getString("Last_Update");
                String updatedBy = myResult.getString("Last_Updated_By");

                Appt appt = new Appt(id, title, start, desc, contact, loc, type, end, custId, userId, createdBy, created, update, updatedBy);
                allAppts.add(appt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppts;
    }
    public static ObservableList<Customer> getCustomerList() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from customers";
            PreparedStatement myPs = sqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();

            while(myResult.next()) {
                int id = myResult.getInt("Customer_ID");
                String name = myResult.getString("Customer_Name");
                String address = myResult.getString("Address");
                String zip = myResult.getString("Postal_Code");
                String phone = myResult.getString("Phone");
                String create = myResult.getString("Create_Date");
                String createdBy = myResult.getString("Created_By");
                String update = myResult.getString("Last_Update");
                String updatedBy = myResult.getString("Last_Updated_By");
                int divId = myResult.getInt("Division_ID");

                String divQuery = String.format("SELECT * FROM first_level_divisions WHERE Division_ID = %d", divId);
                PreparedStatement divPs = sqlCon.getConnection().prepareStatement(divQuery);
                ResultSet divResult = divPs.executeQuery();

//                String country = divResult.getString("Country");
                while(divResult.next()) {
                    String state = divResult.getString("Division");
                    int countryId = divResult.getInt("Country_ID");

                    String countryQuery = String.format("SELECT * FROM countries WHERE Country_ID = %d", countryId);
                    PreparedStatement countryPs = sqlCon.getConnection().prepareStatement(countryQuery);
                    ResultSet countryResult = countryPs.executeQuery();

                    while(countryResult.next()) {
                        String country = countryResult.getString("Country");
                        Customer cust = new Customer(id, name, address, zip, phone, create, createdBy, update, updatedBy, divId, state, country);
                        allCustomers.add(cust);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public static ObservableList<Contact> getContactList() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from contacts";
            PreparedStatement myPs = sqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                int id = myResult.getInt("Contact_ID");
                String name = myResult.getString("Contact_Name");
                Contact contact = new Contact(name, id);
                allContacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }
}
