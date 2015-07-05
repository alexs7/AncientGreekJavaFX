package alrosh7;

import java.util.ArrayList;

/**
 * Created by alex on 05/07/15.
 */
public class Phrase {

    private String value; // 1st field
    private String origin; //2nd field
    private String description; //3rd field
    private String citation; //4th field
    private ArrayList<String> files;
    private String germanTranslation; //5th field
    private String frenchTranslation; //6th field
    private String uniqueID;

    public Phrase(String value, String origin, String description, String citation, String germanTranslation, String frenchTranslation, String uniqueID) {
        this.value = value;
        this.origin = origin;
        this.description = description;
        this.citation = citation;
        this.germanTranslation = germanTranslation;
        this.frenchTranslation = frenchTranslation;
        this.uniqueID = uniqueID;
        this.files = new ArrayList<>();
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public void addToFiles(String fileLocation){
        this.files.add(fileLocation);
    }

    public String getGermanTranslation() {
        return germanTranslation;
    }

    public void setGermanTranslation(String germanTranslation) {
        this.germanTranslation = germanTranslation;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }

    public void setFrenchTranslation(String frenchTranslation) {
        this.frenchTranslation = frenchTranslation;
    }

}
