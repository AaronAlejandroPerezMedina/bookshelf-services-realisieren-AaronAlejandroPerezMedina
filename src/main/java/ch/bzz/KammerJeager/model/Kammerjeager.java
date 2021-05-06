package ch.bzz.KammerJeager.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.FormParam;
import java.util.List;

public class Kammerjeager {


    private List<Werkzeug> werkzeuge;

    private String kammerjeagerID;
    private int maxWerzeuge;

    public List<Werkzeug> getWerkzeuge() {
        return werkzeuge;
    }

    public void setWerkzeuge(List<Werkzeug> werkzeuge) {
        this.werkzeuge = werkzeuge;
    }

    public int getMaxWerzeuge() { return maxWerzeuge;}

    public void setMaxWerzeuge(int maxWerzeuge) {
        this.maxWerzeuge = maxWerzeuge;
    }

    public String getKammerjeagerID() {
        return kammerjeagerID;
    }

    public void setKammerjeagerID(String kammerjeagerID) {
        this.kammerjeagerID = kammerjeagerID;
    }

    public void addWerzeug(Werkzeug werkzeug){
        this.werkzeuge.add(werkzeug);
    }
}