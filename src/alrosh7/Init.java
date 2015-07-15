package alrosh7;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by alex on 05/07/15.
 */
public class Init {

    public Init(){

    }

    public void setTextAreasWrap(Stage stage){
        TextArea destinationTA = (TextArea) stage.getScene().lookup("#descriptionTextArea");
        TextArea originTA = (TextArea) stage.getScene().lookup("#originTextArea");
        TextArea germanTA = (TextArea) stage.getScene().lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) stage.getScene().lookup("#frenchTextArea");

        //TextArea referencesTA = (TextArea) stage.getScene().lookup("#referencesTextArea");
        //referencesTA.setWrapText(true);
        if(destinationTA != null){ destinationTA.setWrapText(true); }
        if(originTA != null){ originTA.setWrapText(true); }
        if(germanTA != null){ germanTA.setWrapText(true); }
        if(frenchTA != null){ frenchTA.setWrapText(true); }
    }

    public void setSearchFieldListener(Stage stage) {
        TextField searchInputTF = (TextField) stage.getScene().lookup("#searchInput");
        searchInputTF.textProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    public static ArrayList<Phrase> loadPhrases(String saveDir) {
        ArrayList<Phrase> localPhrases = new ArrayList<Phrase>();
        File dir = new File(saveDir);
        File[] phrasesFile = dir.listFiles();
        if (phrasesFile != null) {
            for (File file : phrasesFile) {
                try {
                    byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                    String jsonFileString = new String(encoded);
                    JSONObject jsonPhrase = (JSONObject) new JSONParser().parse(jsonFileString);
                    Phrase phrase = new Phrase(
                            jsonPhrase.get("value").toString(),
                            jsonPhrase.get("origin").toString(),
                            jsonPhrase.get("description").toString(),
                            jsonPhrase.get("germanTranslation").toString(),
                            jsonPhrase.get("frenchTranslation").toString(),
                            jsonPhrase.get("uniqueID").toString(),
                            (JSONArray) jsonPhrase.get("files")
                    );
                    System.out.println(phrase);
                    localPhrases.add(phrase);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return localPhrases;
    }
}
