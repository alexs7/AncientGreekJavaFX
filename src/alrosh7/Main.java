package alrosh7;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Greek Phrases Database");
        Init init = new Init();

        Scene mainScene = new Scene(root, 960, 679);
        primaryStage.setScene(mainScene);

        MainController mainController = fxmlLoader.getController();
        mainController.setUpListView(mainScene);

        Image ico = new Image("resources/images/icon.png");
        primaryStage.getIcons().add(ico);
        primaryStage.setResizable(false);

        init.setTextAreasWrap(primaryStage);
        init.setSearchFieldListener(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
