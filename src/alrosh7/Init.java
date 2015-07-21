package alrosh7;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
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
    public static ObservableList<String> comboBoxDataOL = FXCollections.observableArrayList();

    public Init(){

    }

    public static void setTextAreasWrap(Stage stage){
        TextArea destinationTA = (TextArea) stage.getScene().lookup("#descriptionTextArea");
        TextArea originTA = (TextArea) stage.getScene().lookup("#originTextArea");
        TextArea germanTA = (TextArea) stage.getScene().lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) stage.getScene().lookup("#frenchTextArea");
        if(destinationTA != null){ destinationTA.setWrapText(true); }
        if(originTA != null){ originTA.setWrapText(true); }
        if(germanTA != null){ germanTA.setWrapText(true); }
        if(frenchTA != null){ frenchTA.setWrapText(true); }
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
        try {
            ComboBox searchCombobox = (ComboBox) stage.getScene().lookup("#searchInput");
            searchCombobox.setStyle("-fx-font-size : 37pt");
            searchCombobox.setItems(comboBoxDataOL);

            searchCombobox.getEditor().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.isEmpty()) {
                        for (Phrase phrase : inMemoryPhrases) {
                            if (comboBoxDataOL.size() < 6 && phrase.getValue().toLowerCase().contains(newValue.toLowerCase())) {
                                if (!comboBoxDataOL.contains(phrase.getValue())) {
                                    comboBoxDataOL.add(phrase.getValue());
                                }
                            }
                        }
                        searchCombobox.show();
                    } else {
                        comboBoxDataOL.clear();
                        searchCombobox.hide();
                    }
                }
            });
        }catch(Exception e){
            System.out.println(e.toString());
        };
    }

    public static void popUpAlertDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Πληροφόρηση");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
