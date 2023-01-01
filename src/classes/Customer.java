package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private int id;
    private String address;
    private String zip;
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String updatedBy;
    private int divId;

    private String name;
    private String state;
    private String country;


    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(int id, String name, String address, String zip, String phone,
                    String createDate, String createdBy, String lastUpdate, String updatedBy,
                    int divId, String state, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
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
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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
