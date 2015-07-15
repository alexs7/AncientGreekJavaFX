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
import javafx.scene.control.TextField;
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
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements Initializable {

    private Init init;
    private FileAction fileAction;
    private JSONArray savedPhrases;
    private ArrayList<Phrase> inMemoryPhrases;
    private ListView<String> listOfCitationFiles;
    private Scene scene;

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
        init = new Init();
        inMemoryPhrases = new ArrayList<>();

        fileAction = new FileAction();
        fileAction.createRootDir();

        inMemoryPhrases = Init.loadPhrases(fileAction.getRootDirName());
    }

    @FXML
    private void switchToTranslation(MouseEvent mouseEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        TextField valueText = (TextField) stageOfEvent.getScene().lookup("#searchInput");
        TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");
        TextArea descriptionTA = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");

        value = valueText.getText();
        origin = originTA.getText();
        description = descriptionTA.getText();
        citations = new ArrayList<String>(listOfCitationFilesItems);

        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);

        //get the fields again from new scene
        TextArea germanTA = (TextArea) mainScene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) mainScene.lookup("#frenchTextArea");
        TextField valueTextTranslationScreen = (TextField) mainScene.lookup("#searchInput");
        TextArea originTATranslationScreen = (TextArea) mainScene.lookup("#originTextArea");

        valueTextTranslationScreen.setText(value);
        originTATranslationScreen.setText(origin);
        germanTA.setText(germanTranslation);
        frenchTA.setText(frenchTranslation);
    }

    @FXML
    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        TextArea germanTA = (TextArea) stageOfEvent.getScene().lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) stageOfEvent.getScene().lookup("#frenchTextArea");
        TextField valueText = (TextField) stageOfEvent.getScene().lookup("#searchInput");
        TextArea originTA = (TextArea) stageOfEvent.getScene().lookup("#originTextArea");
        TextArea descriptionTA = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");

        value = valueText.getText();
        origin = originTA.getText();
        germanTranslation = germanTA.getText();
        frenchTranslation = frenchTA.getText();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);

        //get the fields again from new scene
        TextField valueTextMainScreen = (TextField) mainScene.lookup("#searchInput");
        TextArea originTAMainScreen = (TextArea) mainScene.lookup("#originTextArea");
        TextArea descriptionTAMainScreen = (TextArea) stageOfEvent.getScene().lookup("#descriptionTextArea");
        valueTextMainScreen.setText(value);
        originTAMainScreen.setText(origin);
        descriptionTAMainScreen.setText(description);
        setUpListView(mainScene); //this is needed as the list needs to be re initiated when switching between scenes
    }

    public void setUpListView(Scene scene) {
        this.scene = scene;
        listOfCitationFiles = (ListView<String>) this.scene.lookup("#listOfCitationFiles");
        listOfCitationFiles.setItems(listOfCitationFilesItems);
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
        ListView<String> listView = (ListView<String>) ((Node) mouseEvent.getSource()).getScene().lookup("#listOfCitationFiles");

        String selectedFileString =  listView.getSelectionModel().getSelectedItem();

        if(selectedFileString == null) { return; }

        System.out.println(Desktop.isDesktopSupported());
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
        System.out.println("updating or saving file");

        //if it does not exist
        uniqueID = UUID.randomUUID().toString();
        JSONObject saveItem = new JSONObject();
        saveItem.put("value", value);
        saveItem.put("origin", origin);
        saveItem.put("description", description);
        saveItem.put("germanTranslation", germanTranslation);
        saveItem.put("frenchTranslation", frenchTranslation);
        saveItem.put("uniqueID", uniqueID);
        JSONArray files = new JSONArray();

        for(int i=0; i<citations.size(); i++){
            files.add(citations.get(i));
        }

        saveItem.put("files", files);
        fileAction.saveFile(saveItem);
    }

    public void loadSelectedPhrase(Phrase phrase, Event event){ // get event from keyboard click
        value = phrase.getValue();
        origin = phrase.getOrigin();
        description = phrase.getDescription();
        citations = phrase.getCitations();
        germanTranslation = phrase.getGermanTranslation();
        frenchTranslation = phrase.getFrenchTranslation();
        uniqueID = phrase.getUniqueID();
    }

}
