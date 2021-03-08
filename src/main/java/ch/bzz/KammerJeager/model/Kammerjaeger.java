package ch.bzz.KammerJeager.model;

import java.util.Collection;
/**
 * @date 08.03.2021
 * @author Aaron Perez
 * @version 1.0
 */
public class Kammerjaeger {

    private Collection<Werkzeuge> werkzeuges;
    private int maxWerkzeuge;
    private Integer kammerjeagerID;
    /**
     * returns werkzeuges
     *
     * @return werkzeuges
     */
    public Collection<Werkzeuge> getWerkzeuge() {
        return werkzeuges;
    }
    /**
     * sets werkzeuges
     *
     * @param werkzeuges
     */
    public void setWerkzeuge(Collection<Werkzeuge> werkzeuges) {
        this.werkzeuges = werkzeuges;
    }
    /**
     * returns maxWerkzeuge
     *
     * @return maxWerkzeuge
     */
    public int getMaxWerkzeuge() {
        return maxWerkzeuge;
    }
    /**
     * sets maxWerkzeuge
     *
     * @param maxWerkzeuge
     */
    public void setMaxWerkzeuge(int maxWerkzeuge) {
        this.maxWerkzeuge = maxWerkzeuge;
    }
    /**
     * returns kammerjeagerID
     *
     * @return kammerjeagerID
     */
    public Integer getKammerjeagerID() {
        return kammerjeagerID;
    }
    /**
     * sets kammerjeagerID
     *
     * @param kammerjeagerID
     */
    public void setKammerjeagerID(Integer kammerjeagerID) {
        this.kammerjeagerID = kammerjeagerID;
    }
}
