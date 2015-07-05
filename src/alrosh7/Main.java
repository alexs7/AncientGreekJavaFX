package alrosh7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Greek Phrases Database");
        Init init = new Init();

        Scene mainScene = new Scene(root, 960, 600);
        primaryStage.setScene(mainScene);

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
