import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Agenda extends BorderPane {
    private Plante plante;
    private Meteo meteo;
    private VBox box;

    public Agenda(VBox box) {
        this.box = box;
        this.getChildren().add(box);
        setButtonActions();
    }

    private void setButtonActions(){
        box.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
        box.getChildren().get(2).setOnMouseClicked(mouseEvent -> getScene().setRoot(meteo));
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }
}
