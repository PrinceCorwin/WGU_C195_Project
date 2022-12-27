package classes;

import databaseHelp.sqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appt {
    private String desc;
    private int contact;
    private String loc;
    private String type;
    private String end;
    private int custId;
    private int userId;
    private String createdBy;
    private String created;
    private String update;
    private String updatedBy;
    private int id;
    private String title;
    private String start;

    public Appt(int id, String title, String start, String desc, int contact, String loc, String type,
                String end, int custId, int userId, String createdBy, String created, String update, String updatedBy) {
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
    this.created = created;
    this.update = update;
    this.updatedBy = updatedBy;


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
    public int getCustId() {
        return custId;
    }
    public void setCustId(int custId) {
        this.custId = custId;
    }
    public int getUserId() {
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
    public String getCreated() {
        return created;
    }
    public void setCreated(String createdBy) {
        this.created = created;
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
}
