package alrosh7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Init init;
    FileAction fileAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init = new Init();
        fileAction = new FileAction();
        fileAction.createRootDir();
        //load json files here
        //do saving here
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
}
