package ch.bzz.KammerJeager.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class Werkzeug {
    private String werkzeugID;
    @FormParam("name")
    @NotEmpty
    @Size(min = 1, max = 10)
    private String name;
    @FormParam("type")
    @NotEmpty
    @Size(min = 1, max = 10)
    private String type;
    @FormParam("colour")
    @NotEmpty
    @Size(min = 1, max = 10)
    private String colour;
    @FormParam("value")
    @Pattern(regexp = "^([0-9][0-9]{0,3}|3000)\\/([0-9][0-9]{0,3}|3000)$")
    @NotEmpty
    @Size(min = 1, max = 10)
    private String value;
    @FormParam("effectiveness")
    @NotEmpty
    @Size(min = 1, max = 10)
    private String effectiveness;
    @FormParam("releaseDate")
    @NotEmpty
    private String releaseDate;

    public Werkzeug(String werkzeugID, @NotEmpty @Size(min = 1, max = 10) String name, @NotEmpty @Size(min = 1, max = 10) String type, @NotEmpty @Size(min = 1, max = 10) String colour, @Pattern(regexp = "^([0-9][0-9]{0,3}|3000)\\/([0-9][0-9]{0,3}|3000)$") @NotEmpty @Size(min = 1, max = 10) String values, @NotEmpty @Size(min = 1, max = 10) String effect, String releaseDate) {
        this.werkzeugID = werkzeugID;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.value = value;
        this.effectiveness = effectiveness;
        this.releaseDate = releaseDate;
    }

    public Werkzeug() {
    }

    public String getWerkzeugID() {
        return werkzeugID;
    }

    public void setWerkzeugID(String werkzeugID) {
        this.werkzeugID = werkzeugID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getValues() {
        return value;
    }

    public void setValues(String value) {
        this.value = value;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}