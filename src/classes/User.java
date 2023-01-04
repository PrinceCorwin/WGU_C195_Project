package classes;

/** This class creates the user as defined by login credentials.
 * @author Steve Corwin Amalfitano.
 */
public class User {
    static String userName;
    static int id;

    /**
     * @return the userName of the user
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * @param name the userName of the successfully logged-in user
     */
    public static void setUserName(String name) {
        userName = name;
    }

    /**
     * @return the id of the user
     */
    public static void setId(int i) {id = i;}
    public int getId() {
        return id;
    }
}
