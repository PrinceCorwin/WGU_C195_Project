package classes;

/** This class creates the Appt (Appointment) object and its attributes.
 * @author Steve Corwin Amalfitano.
 */
public class Appt {
    private String desc;
    private int contact;
    private String loc;
    private String type;
    private String end;
    private int custId;
    private int userId;
    private String createdBy;
    private String createDate;
    private String lastUpdate;
    private String updatedBy;
    private int id;
    private String title;
    private String start;

    /** Initializes new Appt object
     *
     * @param id id of this appt.
     * @param title name of this appt.
     * @param start price of this appt
     * @param desc the description of the appt
     * @param contact the contact_ID associated with the appt
     * @param loc location of the appt
     * @param type the type of appt
     * @param end the end date and time of the appt
     * @param custId the Customer_ID associated with the appt
     * @param userId the User_ID of the user who created the appt
     * @param createdBy the name of the user who created the appt
     * @param createDate the date and time the appt was created
     * @param lastUpdate the date and time the appt was last updated
     * @param updatedBy the name of the user who updated the appt last
     */
    public Appt(int id, String title, String start, String desc, int contact, String loc, String type,
                String end, int custId, int userId, String createdBy, String createDate, String lastUpdate, String updatedBy) {
    this.title = title;
    this.id = id;
    this.start = start;
    this.desc = desc;
    this.contact = contact;
    this.loc = loc;
    this.type = type;
    this.end = end;
    this.custId = custId;
    this.userId = userId;
    this.createdBy = createdBy;
    this.createDate = createDate;
    this.lastUpdate = lastUpdate;
    this.updatedBy = updatedBy;
    }

    /**
     * @return the title of the Appt
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set for the Appt
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the id of the Appt
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id the id to set for the Appt
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the start of the Appt
     */
    public String getStart() {
        return this.start;
    }

    /**
     * @param start the start to set for the Appt
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return the desc of the Appt
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * @param desc the desc to set for the Appt
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the contact associated with the Appt
     */
    public int getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set for the Appt
     */
    public void setContact(int contact) {
        this.contact = contact;
    }

    /**
     * @return the loc of the Appt
     */
    public String getLoc() {
        return loc;
    }

    /**
     * @param loc the loc to set for the Appt
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * @return the type of the Appt
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set for the Appt
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the end of the Appt
     */
    public String getEnd() {
        return end;
    }

    /**
     * @param end the end to set for the Appt
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return the custId associated with the Appt
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * @param custId the custId to set for the Appt
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * @return the userId associated with the appt
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set for the Appt
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the createdBy of the Appt
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set for the Appt
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createDate of the Appt
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set for the Appt
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the lastUpdate of the Appt
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set for the Appt
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the updatedBy of the Appt
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set for the Appt
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
