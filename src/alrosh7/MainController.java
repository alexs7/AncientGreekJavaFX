package alrosh7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Init init;
    private FileAction fileAction;
    private JSONArray savedPhrases;
    private ArrayList<Phrase> inMemoryPhrases;
    private ListView<String> listOfReferenceFiles;
    final ObservableList<String> listOfReferenceFilesItems = FXCollections.observableArrayList();
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init = new Init();
        inMemoryPhrases = new ArrayList<>();

        fileAction = new FileAction();
        fileAction.createRootDir();

        savedPhrases = (JSONArray) fileAction.loadJSONFile().get("phrases");
        Iterator<JSONObject> iterator = savedPhrases.iterator();

        while(iterator.hasNext()){
            JSONObject temp =  iterator.next();
            JSONArray tempFiles = (JSONArray) temp.get("files");

            Phrase phrase = new Phrase(temp.get("value").toString(),
                                    temp.get("origin").toString(),
                                    temp.get("description").toString(),
                                    temp.get("germanTranslation").toString(),
                                    temp.get("frenchTranslation").toString(),
                                    temp.get("uniqueID").toString());


            for (int i = 0; i < tempFiles.size(); i++) {
                JSONObject tmpFile = (JSONObject) tempFiles.get(i);
                phrase.addToCitations(tmpFile.get("citation" + i).toString());
            }

            inMemoryPhrases.add(phrase);
        }
    }

    @FXML
    private void switchToTranslation(MouseEvent mouseEvent) throws IOException {

        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);
    }

    @FXML
    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene mainScene = new Scene(root, 960, 679);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);
    }

    public void setUpListView(Scene scene) {
        this.scene = scene;
        listOfReferenceFiles = (ListView<String>) this.scene.lookup("#listOfReferenceFiles");
        listOfReferenceFiles.setItems(listOfReferenceFilesItems);
    }

//    public void viewFiles(ActionEvent actionEvent) {
//        try {
//            Runtime.getRuntime().exec("nautilus .");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void uploadFiles(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        Stage stageOfEvent = (Stage) ((Node) source).getScene().getWindow();
        ListView<String> tempListView = (ListView<String>) ((Node) source).getScene().lookup("#listOfReferenceFiles");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file...");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File file = fileChooser.showOpenDialog(stageOfEvent);
        if(file !=null){
            listOfReferenceFilesItems.add(file.getAbsolutePath());
        }
        // add the file to the current phrase maybe have a "tempfiles" array and when user clicks save then save them.
    }

    public void openSelectedFiles(MouseEvent mouseEvent){
        ListView<String> listView = (ListView<String>) ((Node) mouseEvent.getSource()).getScene().lookup("#listOfReferenceFiles");

        String selectedFile =  listView.getSelectionModel().getSelectedItem();
        try {
            Desktop.getDesktop().open(new File(selectedFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
