package alrosh7;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by alex on 05/07/15.
 */
public class Init {

    public Init(){

    }

    public void setTextAreasWrap(Stage stage){
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

    public void setSearchFieldListener(Stage stage) {
        TextField searchInputTF = (TextField) stage.getScene().lookup("#searchInput");
        searchInputTF.textProperty().addListener((observable, oldValue, newValue) -> {

        });
    }
}
