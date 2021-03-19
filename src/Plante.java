import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Plante extends BorderPane {
    private VBox box;
    private Agenda agenda;
    private Meteo meteo;

    public Plante(VBox box, Agenda agenda) {
        this.box = box;
        this.getChildren().add(box);
        this.agenda = agenda;
        setButtonActions();
    }

    private void setButtonActions(){
        box.getChildren().get(0).setOnMouseClicked(mouseEvent -> getScene().setRoot(agenda));
        box.getChildren().get(2).setOnMouseClicked(mouseEvent -> getScene().setRoot(meteo));
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }


}
