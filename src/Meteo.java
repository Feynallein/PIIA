import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Meteo extends BorderPane {
    private VBox box;
    private Agenda agenda;
    private Plante plante;

    public Meteo(VBox box, Plante plante, Agenda agenda) {
        this.box = box;
        this.getChildren().add(box);
        this.plante = plante;
        this.agenda = agenda;
        setButtonActions();
    }

    private void setButtonActions(){
        box.getChildren().get(0).setOnMouseClicked(mouseEvent -> getScene().setRoot(agenda));
        box.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
    }
}
