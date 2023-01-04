package classes;

/** This class creates the Division object
 * @author Steve Corwin Amalfitano
 */
public class Division {
    private String name;
    private int id;
    private int countryId;

    /** Initializes new Division object
     *
     * @param name the name of the Division
     * @param id the id of the Division
     * @param countryId the country ID associated with the Division
     */
    public Division(String name, int id, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * @return the name of the division
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name is set for the division
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id of the division
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the name is set for the division
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the countryId of the division
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the name is set for the division
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
