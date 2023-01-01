package classes;

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


    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStart() {
        return this.start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getContact() {
        return contact;
    }
    public void setContact(int contact) {
        this.contact = contact;
    }
    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getCustId() {
        return custId;
    }
    public void setCustId(int custId) {
        this.custId = custId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createdBy) {
        this.createDate = createDate;
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
}
