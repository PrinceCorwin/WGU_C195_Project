package classes;

/** This class creates the Country and its attributes.
 * @author Steve Corwin Amalfitano.
 */
public class Country {
    private String name;
    private int id;

    /** Initializes new Country object
     *
     * @param id id of this Country.
     * @param name name of this Country.
     */
    public Country(String name, int id) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the name of the Country
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set for the Country
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id of the Country
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the Country
     */
    public void setId(int id) {
        this.id = id;
    }
}
