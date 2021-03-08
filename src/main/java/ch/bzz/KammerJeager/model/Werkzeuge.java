package ch.bzz.KammerJeager.model;

import java.util.Date;
/**
 * @date 08.03.2021
 * @author Aaron Perez
 * @version 1.0
 */

public class Werkzeuge {
    private int werkzeugID;
    private String name;
    private String values;
    private String effectiveAgainst;
    private Date date;

    public int getWerkzeugID() {
        return werkzeugID;
    }
    /**
     * sets Werkzeug id
     *
     * @param werkzeugID
     */
    public void setWerkzeugID(int werkzeugID) {
        this.werkzeugID = werkzeugID;
    }
    /**
     * returns name
     *
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * sets name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * returns value
     *
     * @return values
     */
    public String getValues() {
        return values;
    }
    /**
     * sets values
     *
     * @param values
     */
    public void setValues(String values) {
        this.values = values;
    }
    /**
     * returns getEffectiveAgainst
     *
     * @return getEffectiveAgainst
     */
    public String getEffectiveAgainst() {
        return effectiveAgainst;
    }
    /**
     * sets effectiveAgainst
     *
     * @param effectiveAgainst
     */
    public void setEffectiveAgainst(String effectiveAgainst) {
        this.effectiveAgainst = effectiveAgainst;
    }
    /**
     * returns date
     *
     * @return date
     */
    public Date getReleaseDate() {
        return date;
    }
    /**
     * sets date
     *
     * @param date
     */
    public void setReleaseDate(Date date) {
        this.date = date;
    }


}
