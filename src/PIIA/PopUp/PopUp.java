package PIIA.PopUp;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp {
    public PopUp(final Stage stage, PopUpPane pane, String windowName) {
        final Stage eventPopUp = new Stage();
        eventPopUp.setTitle(windowName);
        //eventPopUp.initModality(Modality.APPLICATION_MODAL);
        eventPopUp.initOwner(stage);
        Scene popUpScene = new Scene(pane);
        eventPopUp.setScene(popUpScene);
        eventPopUp.show();
    }
}
