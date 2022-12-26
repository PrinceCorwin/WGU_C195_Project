package classes;

import databaseHelp.sqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private final int id;
    private final String address;
    private final String zip;
    private final String phone;
    private final String create;
    private final String createdBy;
    private final String update;
    private final String updatedBy;
    private final int divId;
    private final String name;
    private final String state;

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private final String country;

    public Customer(int id, String name, String address, String zip, String phone, String create, String createdBy, String update, String updatedBy, int divId, String state, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.create = create;
        this.createdBy = createdBy;
        this.update = update;
        this.updatedBy = updatedBy;
        this.divId = divId;
        this.state = state;
        this.country = country;

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
//                String state = "test";
//                Customer cust = new Customer(id, name, address, zip, phone, create, createdBy, update, updatedBy, divId, state);
//                allCustomers.add(cust);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreate() {
        return create;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdate() {
        return update;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public int getDivId() {
        return divId;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
