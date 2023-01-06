package classes;

/**
 * This class creates creates the Customer class
 * @author Steve Corwin Amalfitano
 */
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

    /** Initializes new Customer object
     *
     * @param id id of this customer.
     * @param name name of this customer.
     * @param address address of this customer
     * @param zip the postal code of this customer
     * @param phone the minimum allowed number of this product in stock
     * @param createDate date and time this customer was created
     * @param createdBy the user that created this customer record
     * @param lastUpdate date and time this customer record was last updated
     * @param updatedBy user that updated this customer record last
     * @param divId id of the first-level-division where this customer is located
     * @param state name of the first-level-division where this customer is located
     * @param country name of the country where this customer is located
     */
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

    /**
     * @return the name of the Customer
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the Customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id of the Customer
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the Customer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the address of the Customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the postal code of the Customer
     */
    public String getZip() {
        return zip;
    }

    /**
     * @return the phone number of the Customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set for the Customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the createDate of the Customer
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set for the Customer
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdBy of the Customer
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set for the Customer
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdate of the Customer
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set for the Customer
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the updatedBe of the Customer
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set for the Customer
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the divId of the Customer
     */
    public int getDivId() {
        return divId;
    }

    /**
     * @param divId the divId to set for the Customer
     */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /**
     * @return the state of the Customer
     */
    public String getState() {
        return state;
    }

    /**
     * @return the country of the Customer
     */
    public String getCountry() {
        return country;
    }
}
