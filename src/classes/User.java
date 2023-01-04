package classes;

public class User {
    static String userName;
    static int id;
    static String password;
//    public User(String name, String password, int id ) {
//        this.id = id;
//        this.userName = name;
//        this.password = password;
//    }
    public static String getUserName() {
        return userName;
    }
    public static void setUserName(String name) {
        userName = name;
    }
    public static void setId(int i) {id = i;}
    public Integer getId() {
        return id;
    }
}
