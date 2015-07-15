package alrosh7;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * Created by alex on 05/07/15.
 */
public class Phrase {

    private String value; // 1st field
    private String origin; //2nd field
    private String description; //3rd field
    private ArrayList<String> citations;
    private String germanTranslation; //5th field
    private String frenchTranslation; //6th field
    private String uniqueID;

    public Phrase(String value, String origin, String description, String germanTranslation, String frenchTranslation, String uniqueID, JSONArray citations) {
        this.value = value;
        this.origin = origin;
        this.description = description;
        this.germanTranslation = germanTranslation;
        this.frenchTranslation = frenchTranslation;
        this.uniqueID = uniqueID;
        this.citations = new ArrayList<>();

        for(Object citation : citations){
            this.citations.add(citation.toString());
        }

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

    public ArrayList<String> getCitations() {
        return citations;
    }

    public void setCitations(ArrayList<String> citations) {
        this.citations = citations;
    }

    public void addToCitations(String fileLocation){
        this.citations.add(fileLocation);
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
