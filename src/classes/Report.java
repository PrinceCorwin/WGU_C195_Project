package classes;

/** This class creates the Report which lists number of appointments per month.
 * @author Steve Corwin Amalfitano.
 */
public class Report {
    private int jan;
    private int feb;
    private int mar;
    private int apr;
    private int may;
    private int jun;
    private int jul;
    private int aug;
    private int sep;
    private int oct;
    private int nov;
    private int dec;
    private int id;
    private String type;

    /** Initializes new Report
     *
     * @param jan count of appointments in jan.
     * @param feb count of appointments in feb.
     * @param mar count of appointments in mar.
     * @param apr count of appointments in apr.
     * @param may count of appointments in may.
     * @param jun count of appointments in jun.
     * @param jul count of appointments in jul.
     * @param aug count of appointments in aug.
     * @param sep count of appointments in sep.
     * @param oct count of appointments in oct.
     * @param nov count of appointments in nov.
     * @param dec count of appointments in dec.
     * @param id the id of the contact associated with the report.
     * @param type the type of appointment associated with the report
     */
    public Report(int jan, int feb, int mar, int apr, int may, int jun, int jul, int aug, int sep, int oct, int nov, int dec, int id, String type) {
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
        this.id = id;
        this.type = type;
    }

    /**
     * @return the count of reports in jan
     */
    public int getJan() {
        return jan;
    }

    /**
     * @param jan sets the count reports in jan for the report
     */
    public void setJan(int jan) {
        this.jan = jan;
    }

    /**
     * @return the count of reports in feb
     */
    public int getFeb() {
        return feb;
    }

    /**
     * @param feb sets the count reports in jan for the report
     */
    public void setFeb(int feb) {
        this.feb = feb;
    }

    /**
     * @return the count of reports in mar
     */
    public int getMar() {
        return mar;
    }

    /**
     * @param mar sets the count reports in jan for the report
     */
    public void setMar(int mar) {
        this.mar = mar;
    }

    /**
     * @return the count of reports in apr
     */
    public int getApr() {
        return apr;
    }

    /**
     * @param apr sets the count reports in jan for the report
     */
    public void setApr(int apr) {
        this.apr = apr;
    }

    /**
     * @return the count of reports in may
     */
    public int getMay() {
        return may;
    }

    /**
     * @param may sets the count reports in jan for the report
     */
    public void setMay(int may) {
        this.may = may;
    }

    /**
     * @return the count of reports in jun
     */
    public int getJun() {
        return jun;
    }

    /**
     * @param jun sets the count reports in jan for the report
     */
    public void setJun(int jun) {
        this.jun = jun;
    }

    /**
     * @return the count of reports in jul
     */
    public int getJul() {
        return jul;
    }

    /**
     * @param jul sets the count reports in jan for the report
     */
    public void setJul(int jul) {
        this.jul = jul;
    }

    /**
     * @return the count of reports in aug
     */
    public int getAug() {
        return aug;
    }

    /**
     * @param aug sets the count reports in jan for the report
     */
    public void setAug(int aug) {
        this.aug = aug;
    }

    /**
     * @return the count of reports in sep
     */
    public int getSep() {
        return sep;
    }

    /**
     * @param sep sets the count reports in jan for the report
     */

    public void setSep(int sep) {
        this.sep = sep;
    }

    /**
     * @return the count of reports in oct
     */
    public int getOct() {
        return oct;
    }

    /**
     * @param oct sets the count reports in jan for the report
     */
    public void setOct(int oct) {
        this.oct = oct;
    }

    /**
     * @return the count of reports in nov
     */
    public int getNov() {
        return nov;
    }

    /**
     * @param nov sets the count reports in jan for the report
     */
    public void setNov(int nov) {
        this.nov = nov;
    }

    /**
     * @return the count of reports in dec
     */
    public int getDec() {
        return dec;
    }

    /**
     * @param dec sets the count reports in jan for the report
     */
    public void setDec(int dec) {
        this.dec = dec;
    }

    /**
     * @return the count of reports in jan
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the contact Id for the report
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the type or appointment associated with the report
     */
    public String getType() {
        return type;
    }

    /**
     * @param type sets the type of appointment associated with the report
     */
    public void setType(String type) {
        this.type = type;
    }
}
