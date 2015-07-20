package alrosh7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.control.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements Initializable {

    private FileAction fileAction;
    private ListView<String> listOfCitationFilesListView;
    private static String value;
    private static String origin;
    private static String description;
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
        Scene scene = ((Node) mouseEvent.getSource()).getScene();

        TextArea descriptionTA = (TextArea) scene.lookup("#descriptionTextArea");
        TextArea germanTA = (TextArea) scene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) scene.lookup("#frenchTextArea");
        ListView listViewfiles = (ListView) scene.lookup("#listOfCitationFilesListView");
        Text citationsText = (Text) scene.lookup ("#citationsText");
        Text switchToTranslationButton = (Text) scene.lookup ("#switchToTranslationButton");
        Text switchToGreekButton = (Text) scene.lookup ("#switchToGreekButton");

        descriptionTA.setVisible(false);
        listViewfiles.setVisible(false);
        citationsText.setVisible(false);
        switchToTranslationButton.setVisible(false);
        switchToGreekButton.setVisible(true);
        germanTA.setVisible(true);
        frenchTA.setVisible(true);

        String image = Main.class.getResource("../resources/images/secondaryBackground.jpg").toExternalForm();
        scene.lookup("#main").setStyle("-fx-background-image: url('"+image+"');");
    }

    @FXML
    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        Scene scene = ((Node) mouseEvent.getSource()).getScene();

        TextArea descriptionTA = (TextArea) scene.lookup("#descriptionTextArea");
        TextArea germanTA = (TextArea) scene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) scene.lookup("#frenchTextArea");
        ListView listViewfiles = (ListView) scene.lookup("#listOfCitationFilesListView");
        Text citationsText = (Text) scene.lookup ("#citationsText");
        Text switchToTranslationButton = (Text) scene.lookup ("#switchToTranslationButton");
        Text switchToGreekButton = (Text) scene.lookup ("#switchToGreekButton");

        descriptionTA.setVisible(true);
        listViewfiles.setVisible(true);
        citationsText.setVisible(true);
        switchToTranslationButton.setVisible(true);
        switchToGreekButton.setVisible(false);
        germanTA.setVisible(false);
        frenchTA.setVisible(false);

        String image = Main.class.getResource("../resources/images/mainBackground.jpg").toExternalForm();
        scene.lookup("#main").setStyle("-fx-background-image: url('" + image + "');");
    }

    public void setUpListView(Scene scene) {
        listOfCitationFilesListView = (ListView<String>) scene.lookup("#listOfCitationFilesListView");
        listOfCitationFilesListView.setItems(listOfCitationFilesItems);
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
    }

    public void openSelectedFiles(MouseEvent mouseEvent){
        ListView<String> listView = (ListView<String>) ((Node) mouseEvent.getSource()).getScene().lookup("#listOfCitationFilesListView");

        String selectedFileString = listView.getSelectionModel().getSelectedItem();

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
        setVariablesFromAnyViewComponentVisible(event);

        for(Phrase phrase : Init.inMemoryPhrases){
            if(phrase.getValue().equals(value) ){ canSave = false ; };
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

            for(String reference : listOfCitationFilesItems){
                files.add(reference);
            }

            saveItem.put("files", files);
            addPhraseToInMemoryPhrases(saveItem);
            fileAction.saveFile(saveItem);
            Init.popUpAlertDialog("Αποθηκεύτηκε!");
        }else{
            Init.popUpAlertDialog("Αυτή η φράση ήδη υπάρχει! Δεν θα αποθηκευτεί");
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
        Scene scene = ((Node) event.getSource()).getScene();

        ComboBox searchCombobox = (ComboBox) scene.lookup("#searchInput");
        String selectedString = (String) searchCombobox.getSelectionModel().getSelectedItem();

        Phrase phraseToLoad = null;
        for (Phrase phrase : Init.inMemoryPhrases) {
            if (phrase.getValue() == selectedString) {
                phraseToLoad = phrase;
            }
        }
        loadSelectedPhrase(phraseToLoad, event);
    }

    public void loadSelectedPhrase(Phrase phrase, Event event){ // get event from keyboard click

        if(phrase == null) { return; };

        Scene scene = ((Node) event.getSource()).getScene();

        value = phrase.getValue();
        origin = phrase.getOrigin();
        description = phrase.getDescription();
        listOfCitationFilesItems.clear();
        for(String reference : phrase.getReferences()){
            listOfCitationFilesItems.add(reference);
        }
        germanTranslation = phrase.getGermanTranslation();
        frenchTranslation = phrase.getFrenchTranslation();
        uniqueID = phrase.getUniqueID();

        TextArea descriptionTA = (TextArea) scene.lookup("#descriptionTextArea");
        TextArea germanTA = (TextArea) scene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) scene.lookup("#frenchTextArea");
        TextArea originTA = (TextArea) scene.lookup("#originTextArea");

        descriptionTA.setText(description);
        germanTA.setText(germanTranslation);
        frenchTA.setText(frenchTranslation);
        originTA.setText(origin);
    }

    public void cleanInputs(Event event) {
        Scene scene = ((Node) event.getSource()).getScene();

        value = "";
        origin = "";
        description = "";
        listOfCitationFilesItems.clear();
        germanTranslation = "";
        frenchTranslation = "";
        uniqueID = "";

        TextArea germanTA = (TextArea) scene.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) scene.lookup("#frenchTextArea");
        TextArea originTA = (TextArea) scene.lookup("#originTextArea");
        TextArea descriptionTA = (TextArea) scene.lookup("#descriptionTextArea");

        if(germanTA !=null){ germanTA.setText(germanTranslation); }
        if(frenchTA !=null){ frenchTA.setText(frenchTranslation); }
        if(originTA !=null){ originTA.setText(origin); }
        if(descriptionTA !=null){ descriptionTA.setText(description); }
    }

    public void setVariablesFromAnyViewComponentVisible(Event event) {
        Scene scence = ((Node) event.getSource()).getScene();

        TextArea germanTA = (TextArea) scence.lookup("#germanTextArea");
        TextArea frenchTA = (TextArea) scence.lookup("#frenchTextArea");
        TextArea originTA = (TextArea) scence.lookup("#originTextArea");
        TextArea descriptionTA = (TextArea) scence.lookup("#descriptionTextArea");
        ComboBox searchInput = (ComboBox) scence.lookup("#searchInput");

        if(searchInput != null){ value = searchInput.getEditor().getText(); }
        if(germanTA !=null){ germanTranslation = germanTA.getText(); }
        if(frenchTA !=null){ frenchTranslation = frenchTA.getText(); }
        if(originTA !=null){  origin = originTA.getText(); }
        if(descriptionTA !=null){ description = descriptionTA.getText(); }
    }

    public void deletePhrase(Event event) {
        if(uniqueID != "") {
            File fileToDelete = new File(fileAction.getRootDirName() + "/" + uniqueID + ".json");
            if (fileToDelete.exists()) {
                fileToDelete.delete();
                Init.popUpAlertDialog("Διαγράφηκε!");
                cleanInputs(event);
            }
        }
    }
}
