package alrosh7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
                                    temp.get("citation").toString(),
                                    temp.get("germanTranslation").toString(),
                                    temp.get("frenchTranslation").toString(),
                                    temp.get("uniqueID").toString());


            for (int i = 0; i < tempFiles.size(); i++) {
                JSONObject tmpFile = (JSONObject) tempFiles.get(i);
                phrase.addToFiles(tmpFile.get("file"+i).toString());
            }

            inMemoryPhrases.add(phrase);
        }
    }

    @FXML
    private void switchToTranslation(ActionEvent actionEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        Scene mainScene = new Scene(root, 960, 600);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);
    }

    @FXML
    public void switchToMainScene(ActionEvent actionEvent) throws IOException {
        Stage stageOfEvent = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene mainScene = new Scene(root, 960, 600);
        stageOfEvent.setScene(mainScene);

        init.setTextAreasWrap(stageOfEvent);
    }

    public void viewFiles(ActionEvent actionEvent) {
        try {
            Runtime.getRuntime().exec("nautilus .");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFiles(ActionEvent actionEvent) {
        Stage stageOfEvent = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file...");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        File file = fileChooser.showOpenDialog(stageOfEvent);
        // add the file to the current phrase maybe have a "tempfiles" array and when user clicks save then save them.
    }
}
