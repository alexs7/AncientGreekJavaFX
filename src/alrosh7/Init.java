package alrosh7;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by alex on 05/07/15.
 */
public class Init {
    public Init(){

    }

    public void setTextAreasWrap(Stage stage){
        TextArea originTA = (TextArea) stage.getScene().lookup("#originTextArea");
        TextArea referencesTA = (TextArea) stage.getScene().lookup("#referencesTextArea");
        referencesTA.setWrapText(true);
        originTA.setWrapText(true);
    }

}
