package classes;

import databaseHelp.sqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int id;
    private String address;
    private String zip;
    private String phone;
    private String create;
    private String createdBy;
    private String update;
    private String updatedBy;
    private int divId;
    private String name;
    private String state;
    private String country;


    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(int id, String name, String address, String zip, String phone,
                    String create, String createdBy, String update, String updatedBy,
                    int divId, String state, String country) {
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCreate() {
        return create;
    }
    public void setCreate(String create) {
        this.create = create;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getUpdate() {
        return update;
    }
    public void setUpdate(String update) {
        this.update = update;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public int getDivId() {
        return divId;
    }
    public void setDivId(int divId) {
        this.divId = divId;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

}
