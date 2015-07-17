package alrosh7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.Observable;

/**
 * Created by alex on 05/07/15.
 */
public class Init {

    public static ArrayList<Phrase> inMemoryPhrases;
    public static ObservableList<String> comboBoxDataOL;

    public Init(){

    }

    public static void setTextAreasWrap(Stage stage){
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

    public static void setSearchFieldListener(Stage stage) {
//        TextField searchInputTF = (TextField) stage.getScene().lookup("#searchInput");
//        searchInputTF.textProperty().addListener((observable, oldValue, newValue) -> {
//
//        });
    }

    public static ArrayList<Phrase> loadPhrases(String saveDir) {
        inMemoryPhrases = new ArrayList<>();

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
                            jsonPhrase.get("value") != null ? jsonPhrase.get("value").toString() : "",
                            jsonPhrase.get("origin") != null ? jsonPhrase.get("origin").toString() : "",
                            jsonPhrase.get("description") != null ? jsonPhrase.get("description").toString() : "",
                            jsonPhrase.get("germanTranslation") != null ? jsonPhrase.get("germanTranslation").toString() : "",
                            jsonPhrase.get("frenchTranslation") != null ? jsonPhrase.get("frenchTranslation").toString() : "",
                            jsonPhrase.get("uniqueID") != null ? jsonPhrase.get("uniqueID").toString() : "",
                            (JSONArray) jsonPhrase.get("files")
                    );
                    localPhrases.add(phrase);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        inMemoryPhrases = localPhrases;
        return localPhrases;
    }

    public static void setUpComboBox(Stage stage) {
        comboBoxDataOL = FXCollections.observableArrayList(Init.setPhrasesCombinedIDValues());

        ComboBox searchCombobox = (ComboBox) stage.getScene().lookup("#searchInput");
        searchCombobox.setStyle("-fx-font-size : 37pt");
        searchCombobox.setItems(comboBoxDataOL);

        searchCombobox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue == "") { //reset
                comboBoxDataOL = FXCollections.observableArrayList(Init.setPhrasesCombinedIDValues());
            }

            String keyWord = newValue;
            ArrayList<String> matchingStrings = new ArrayList<String>();
            matchingStrings.add("bae");

//            for (int i = 0; i <= comboBoxDataOL.size() - 1; i++) {
//                String stringToCheck = comboBoxDataOL.get(i);
//                if (matches(keyWord, stringToCheck) && matchingStrings.size() < 6) {
//                    matchingStrings.add(comboBoxDataOL.get(i));
//                }
//            }

//            ObservableList<String> matchingStringsOL = FXCollections.observableArrayList(matchingStrings); //THIS BREAKS IT
//            searchCombobox.setItems(matchingStringsOL);

            if (1 > 0 && !(newValue + oldValue).isEmpty()) {
                searchCombobox.show();
            } else {
                searchCombobox.hide();
            }

        });
    }

    private static boolean matches(String keyWord, String stringToCheck) {
        return stringToCheck.contains(keyWord);
    }

    public static ArrayList<String> setPhrasesCombinedIDValues() {
        ArrayList<String> comboBoxValues = new ArrayList<String>();
        for (Phrase phrase : inMemoryPhrases){
            String str = phrase.getValue();
            comboBoxValues.add(str);
        }
        return comboBoxValues;
    }
}
