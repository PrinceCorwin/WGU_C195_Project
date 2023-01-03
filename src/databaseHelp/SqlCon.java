package databaseHelp;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TimeZone;

public abstract class SqlCon {
    private static final String sqlProtocol = "jdbc";
    private static final String splVend = ":mysql:";
    private static final String sqlDbLoc = "//localhost/";
    private static final String sqlDbName = "client_schedule";
    private static final String sqlIp = sqlProtocol + splVend + sqlDbLoc + sqlDbName + "?connectionTimeZone = SERVER";
    private static final String sqlDriver = "com.mysql.cj.jdbc.Driver";
    private static final String appUser = "sqlUser";
    public static Connection appConn;

    public static void openConnection()
    {
        try {
            Class.forName(sqlDriver);
            // Password
            String password = "Passw0rd!";
            appConn = DriverManager.getConnection(sqlIp, appUser, password);
            System.out.println("You are connected");
        }
        catch(Exception e) {
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
    public static ObservableList<Appt> getApptList(String view) {
        ObservableList<Appt> allAppts = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from appointments";
            if (Objects.equals(view, "week"))  {
                query = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(NOW())";
            } else if (Objects.equals(view, "month")) {
                query = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(NOW())";
            } else if (Objects.equals(view, "alert")) {
                String currentUtcTime = Helper.getCurrentUtcTime();
                query = String.format("SELECT * FROM appointments WHERE Start BETWEEN " +
                        "('%s' - INTERVAL 15 MINUTE) AND ('%s' + INTERVAL 15 MINUTE)", currentUtcTime, currentUtcTime);
            }
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();

            while(myResult.next()) {
                int id = myResult.getInt("Appointment_ID");
                String title = myResult.getString("Title");
                String start = Helper.utcToLocal(myResult.getString("Start"));
                String desc = myResult.getString("Description");
                int contact = myResult.getInt("Contact_ID");
                String loc = myResult.getString("Location");
                String type = myResult.getString("Type");
                String end = Helper.utcToLocal(myResult.getString("End"));
                int custId = myResult.getInt("Customer_ID");
                int userId = myResult.getInt("User_ID");
                String createdBy = myResult.getString("Created_By");
                String createDate = Helper.utcToLocal(myResult.getString("Create_Date"));
                String lastUpdate = Helper.utcToLocal(myResult.getString("Last_Update"));
                String updatedBy = myResult.getString("Last_Updated_By");

                Appt appt = new Appt(id, title, start, desc, contact, loc, type, end, custId, userId, createdBy, createDate, lastUpdate, updatedBy);
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
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();

            while(myResult.next()) {
                int id = myResult.getInt("Customer_ID");
                String name = myResult.getString("Customer_Name");
                String address = myResult.getString("Address");
                String zip = myResult.getString("Postal_Code");
                String phone = myResult.getString("Phone");
                String createDate = myResult.getString("Create_Date");
                String createdBy = myResult.getString("Created_By");
                String update = myResult.getString("Last_Update");
                String updatedBy = myResult.getString("Last_Updated_By");
                int divId = myResult.getInt("Division_ID");

                String divQuery = String.format("SELECT * FROM first_level_divisions WHERE Division_ID = %d", divId);
                PreparedStatement divPs = SqlCon.getConnection().prepareStatement(divQuery);
                ResultSet divResult = divPs.executeQuery();

                while(divResult.next()) {
                    String state = divResult.getString("Division");
                    int countryId = divResult.getInt("Country_ID");

                    String countryQuery = String.format("SELECT * FROM countries WHERE Country_ID = %d", countryId);
                    PreparedStatement countryPs = SqlCon.getConnection().prepareStatement(countryQuery);
                    ResultSet countryResult = countryPs.executeQuery();

                    while(countryResult.next()) {
                        String country = countryResult.getString("Country");
                        Customer cust = new Customer(id, name, address, zip, phone, createDate, createdBy, update, updatedBy, divId, state, country);
                        allCustomers.add(cust);
                    }
                }
            }
        } catch (SQLException e) {
        }
        return allCustomers;
    }

    public static ObservableList<Contact> getContactList() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from contacts";
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
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
    public static void deleteCust(Customer deletedCust) {
        try {
            int id = deletedCust.getId();
            String custQuery = String.format("DELETE FROM customers WHERE Customer_ID = %d", id);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(custQuery);
            myPs.executeUpdate();

        } catch (SQLException e) {
        }
    }
    public static void deleteAppt(Appt deletedAppt) {
        try {
            int id = deletedAppt.getId();
            String custQuery = String.format("DELETE FROM appointments WHERE Appointment_ID = %d", id);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(custQuery);
            myPs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Integer> getUserIds() {
        ObservableList<Integer> allUserIds = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from users";
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                int id = myResult.getInt("User_ID");
                allUserIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserIds;
    }

    public static boolean verifyOverlap(String start, String end, int custId) {
        try {
            String query = String.format("SELECT * from appointments WHERE Customer_ID = %d", custId);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                int id = myResult.getInt("Customer_ID");
                if (custId == id) {
                    String checkStart = myResult.getString("Start");
                    String checkEnd = myResult.getString("End");
                    SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    java.util.Date existingStart;
                    java.util.Date existingEnd;
                    java.util.Date newStart;
                    java.util.Date newEnd;
                    try {
                        existingStart = utcFormat.parse(checkStart);
                        existingEnd = utcFormat.parse(checkEnd);
                        newStart = utcFormat.parse(start);
                        newEnd = utcFormat.parse(end);
                        if (newStart.after(existingStart) && newStart.before(existingEnd)) {
                            return false;
                        }
                        if (newEnd.after(existingStart) && newEnd.before(existingEnd)) {
                            return false;
                        }
                    } catch (ParseException e) {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    return true;
    }

    public static ObservableList<Country> getCountryList() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from countries";
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                String name = myResult.getString("Country");
                int id = myResult.getInt("Country_ID");
                Country country = new Country(name, id);
                allCountries.add(country);
            }
        } catch (SQLException e) {
        }
        return allCountries;
    }
    public static ObservableList<Division> getDivisionList() {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from first_level_divisions";
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                String name = myResult.getString("Division");
                int id = myResult.getInt("Division_ID");
                int countryId = myResult.getInt("Country_ID");
                Division division = new Division(name, id, countryId);
                allDivisions.add(division);
            }
        } catch (SQLException e) {
        }
        return allDivisions;
    }

    public static int getCountryIdFromName(String name) {
        int id = 0;
        try {
            String query = String.format("SELECT * from countries WHERE Country = '%s'", name);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                id = myResult.getInt("Country_ID");
            }
        } catch (SQLException e) {
        }
        return id;
    }

    public static int getDivIdFromName(String name) {
        int id = 0;
        try {
            String query = String.format("SELECT * from first_level_divisions WHERE Division = '%s'", name);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                id = myResult.getInt("Division_ID");
            }
        } catch (SQLException e) {
        }
        return id;
    }


    public static int getNumAppts(Integer id, int i) {
        int count = 0;
        try {
            String query = String.format("SELECT * from appointments WHERE User_ID = %d AND MONTH(Start) = %d", id, i);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static int getApptsByType(String type, int i) {
        int count = 0;
        try {
            String query = String.format("SELECT * from appointments WHERE Type = '%s' AND MONTH(Start) = %d", type, i);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                count++;
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static ArrayList<String> getAllTypes() {
        ArrayList<String> types = new ArrayList<>();
        ObservableList<Appt> allAppts = getApptList("all");
        for (Appt a : allAppts) {
            String thisType = a.getType();
            if (!types.contains(thisType)) {
                types.add(thisType);
            }
        }
        return types;
    }

    public static ObservableList<Appt> getApptsByContact(int contactId) {
        ObservableList<Appt> appts = FXCollections.observableArrayList();
        try {
            String query = String.format("SELECT * from appointments WHERE Contact_ID = %d", contactId);
            PreparedStatement myPs = SqlCon.getConnection().prepareStatement(query);
            ResultSet myResult = myPs.executeQuery();
            while(myResult.next()) {
                int apptId = myResult.getInt("Appointment_ID");
                String title = myResult.getString("Title");
                String type = myResult.getString("Type");
                String desc = myResult.getString("Description");
                String start = myResult.getString("Start");
                String end = myResult.getString("End");
                int custId = myResult.getInt("Customer_ID");

                Appt appt = new Appt(apptId, title, start, desc, contactId, "", type, end, custId, 0, "", "", "", "");
                appts.add(appt);
            }
        } catch (SQLException e) {
        }
        return appts;
    }
}
