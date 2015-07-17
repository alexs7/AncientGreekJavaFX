package alrosh7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements Initializable {

    private FileAction fileAction;
    private ListView<String> listOfCitationFilesListView;
    //tempSaveVariables
    private static String value;
    private static String origin;
    private static String description;
    private static ArrayList<String> citations;
    private static String germanTranslation;
    private static String frenchTranslation;
    private static String uniqueID;
    private static ObservableList<String> listOfCitationFilesItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileAction = new FileAction();
        fileAction.createRootDir();
    }

    @FXML
    private void switchToTranslation(MouseEvent mouseEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        ComboBox valueText = (ComboBox) stageOfEvent.getScene().lookup("#searchInput");
        TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");
        TextArea descriptionTA = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");

        value = valueText.getEditor().getText();
        origin = originTA.getText();
        description = descriptionTA.getText();
        citations = new ArrayList<String>(listOfCitationFilesItems);

        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        Init.setTextAreasWrap(stageOfEvent);

        //get the fields again from new scene
        TextArea germanTA = (TextArea) mainScene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) mainScene.lookup("#frenchTextArea");
        ComboBox valueTextTranslationScreen = (ComboBox) mainScene.lookup("#searchInput");
        TextArea originTATranslationScreen = (TextArea) mainScene.lookup("#originTextArea");

        valueTextTranslationScreen.getEditor().setText(value);
        originTATranslationScreen.setText(origin);
        germanTA.setText(germanTranslation);
        frenchTA.setText(frenchTranslation);

        Init.setUpComboBox(stageOfEvent);
    }

    @FXML
    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        TextArea germanTA = (TextArea) stageOfEvent.getScene().lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) stageOfEvent.getScene().lookup("#frenchTextArea");
        ComboBox valueText = (ComboBox) stageOfEvent.getScene().lookup("#searchInput");
        TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");

        value = valueText.getEditor().getText();
        origin = originTA.getText();
        germanTranslation = germanTA.getText();
        frenchTranslation = frenchTA.getText();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        Init.setTextAreasWrap(stageOfEvent);

        //get the fields again from new scene
        ComboBox valueTextMainScreen = (ComboBox) mainScene.lookup("#searchInput");
        TextArea originTAMainScreen = (TextArea) mainScene.lookup("#originTextArea");
        TextArea descriptionTAMainScreen = (TextArea) mainScene.lookup("#descriptionTextArea");

        valueTextMainScreen.getEditor().setText(value);
        originTAMainScreen.setText(origin);
        descriptionTAMainScreen.setText(description);
        setUpListView(mainScene); //this is needed as the list needs to be re initiated when switching between scenes
        //Init.setUpComboBox(stageOfEvent);
    }

    public void setUpListView(Scene scene) {
        listOfCitationFilesListView = (ListView<String>) scene.lookup("#listOfCitationFilesListView");
        if(listOfCitationFilesListView != null) {
            listOfCitationFilesListView.setItems(listOfCitationFilesItems);
        }
    }

    public void uploadFiles(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        Stage stageOfEvent = (Stage) ((Node) source).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file...");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File file = fileChooser.showOpenDialog(stageOfEvent);
        if(file !=null){
            listOfCitationFilesItems.add(file.getAbsolutePath());
        }
        // add the file to the current phrase maybe have a "tempfiles" array and when user clicks save then save them.
    }

    public void openSelectedFiles(MouseEvent mouseEvent){
        ListView<String> listView = (ListView<String>) ((Node) mouseEvent.getSource()).getScene().lookup("#listOfCitationFilesListView");

        String selectedFileString =  listView.getSelectionModel().getSelectedItem();

        if(selectedFileString == null) { return; }

        //System.out.println(Desktop.isDesktopSupported());
        File fileToOpen = new File(selectedFileString);
        System.out.println(fileToOpen.exists());

//        if(!Desktop.isDesktopSupported()){
//            System.out.println("Desktop is not supported");
//            return;
//        }
//
//        Desktop desktop = Desktop.getDesktop();
//
//        // after check if file exists and open it
//        if(fileToOpen.exists()) try {
//            desktop.open(fileToOpen);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void updateOrCreatePhrase(Event event) {
        //if it does not exist
        boolean canSave = true;

        for(Phrase phrase : Init.inMemoryPhrases){
            if(phrase.getValue() == value ){ canSave = false ; };
        }

        if(canSave) {
            uniqueID = UUID.randomUUID().toString();
            JSONObject saveItem = new JSONObject();
            saveItem.put("value", value);
            saveItem.put("origin", origin);
            saveItem.put("description", description);
            saveItem.put("germanTranslation", germanTranslation);
            saveItem.put("frenchTranslation", frenchTranslation);
            saveItem.put("uniqueID", uniqueID);
            JSONArray files = new JSONArray();

            if(citations != null) {
                for (int i = 0; i < citations.size(); i++) {
                    files.add(citations.get(i));
                }
            }

            saveItem.put("files", files);
            addPhraseToInMemoryPhrases(saveItem);
            fileAction.saveFile(saveItem);
        }
    }

    private void addPhraseToInMemoryPhrases(JSONObject saveItem) {
        Phrase phrase = new Phrase(
                saveItem.get("value") != null ? saveItem.get("value").toString() : "",
                saveItem.get("origin") != null ? saveItem.get("origin").toString() : "",
                saveItem.get("description") != null ? saveItem.get("description").toString() : "",
                saveItem.get("germanTranslation") != null ? saveItem.get("germanTranslation").toString() : "",
                saveItem.get("frenchTranslation") != null ? saveItem.get("frenchTranslation").toString() : "",
                saveItem.get("uniqueID") != null ? saveItem.get("uniqueID").toString() : "",
                (JSONArray) saveItem.get("files")
        );
        Init.inMemoryPhrases.add(phrase);
    }

    public void loadPhrase(Event event){
        ComboBox searchCombobox = (ComboBox) ((Node) event.getSource()).getScene().lookup("#searchInput");
        String selectedString = (String) searchCombobox.getSelectionModel().getSelectedItem();

        System.out.println("selectedString "+selectedString);
        System.out.println("searchCombobox.getEditor().getText() " + searchCombobox.getEditor().getText());

        Phrase phraseToLoad = null;
        for (Phrase phrase : Init.inMemoryPhrases) {
            if (phrase.getValue() == selectedString) {
                phraseToLoad = phrase;
            }
        }

        if (phraseToLoad == null || event == null) {
            System.out.println("Phrase/Event not found not loading one");
        } else {
            loadSelectedPhrase(phraseToLoad, event);
        }
    }

    public void loadSelectedPhrase(Phrase phrase, Event event){ // get event from keyboard click
        value = phrase.getValue();
        origin = phrase.getOrigin();
        description = phrase.getDescription();
        citations = phrase.getCitations();
        listOfCitationFilesItems.clear();
        for(String citation : citations){
            listOfCitationFilesItems.add(citation);
        }
        germanTranslation = phrase.getGermanTranslation();
        frenchTranslation = phrase.getFrenchTranslation();
        uniqueID = phrase.getUniqueID();

        //set all fields regarding on which screen you are
        screenSetFields(event);
    }

    public void screenSetFields(Event event) {
        Stage stageOfEvent = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if(stageOfEvent != null) {
            TextArea germanTA = (TextArea) stageOfEvent.getScene().lookup("#germanTextArea");
            TextArea frenchTA = (TextArea) stageOfEvent.getScene().lookup("#frenchTextArea");
            TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");
            TextArea descriptionTA = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");
            setUpListView(((Node) event.getSource()).getScene());

            if(germanTA !=null){ germanTA.setText(germanTranslation); }
            if(frenchTA !=null){ frenchTA.setText(frenchTranslation); }
            if(originTA !=null){ originTA.setText(origin); }
            if(descriptionTA !=null){ descriptionTA.setText(description); }
        }
    }

    public void cleanInputs(Event event) {
        Stage stageOfEvent = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stageOfEvent != null) {
            value = "";
            origin = "";
            description = "";
            listOfCitationFilesItems.clear();
            germanTranslation = "";
            frenchTranslation = "";
            uniqueID = "";

            //ComboBox searchInput = (ComboBox) stageOfEvent.getScene().lookup("#searchInput");
            TextArea germanTA = (TextArea) stageOfEvent.getScene().lookup("#germanTextArea");
            TextArea frenchTA = (TextArea) stageOfEvent.getScene().lookup("#frenchTextArea");
            TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");
            TextArea descriptionTA = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");
            setUpListView(((Node) event.getSource()).getScene());

            //if(searchInput != null){ searchInput.setValue(null); }
            if(germanTA !=null){ germanTA.setText(germanTranslation); }
            if(frenchTA !=null){ frenchTA.setText(frenchTranslation); }
            if(originTA !=null){ originTA.setText(origin); }
            if(descriptionTA !=null){ descriptionTA.setText(description); }
        }
    }
}
