package classes;

/** This class creates the Contact object and its attributes.
 * @author Steve Corwin Amalfitano.
 */
public class Contact {
    private String name;
    private int id;

    /** Initializes new Contact object
     *
     * @param id id of this product.
     * @param name name of this product.
     */
    public Contact(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * @return the name of the Contact
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the Contact
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id of the Contact
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the Contact
     */
    public void setId(int id) {
        this.id = id;
    }
}
