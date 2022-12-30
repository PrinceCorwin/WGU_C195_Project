package classes;

public class User {
    static String userName = "Steve";
    int id;
    public User(String name, int id) {
        this.id = id;
        this.userName = name;
    }
    public static String getUserName() {
        return userName;
    }
    public static void setUserName(String name) {
        userName = name;
    }

    public Integer getId() {
        return id;
    }
}
