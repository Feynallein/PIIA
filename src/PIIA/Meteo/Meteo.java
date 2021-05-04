package PIIA.Meteo;

import PIIA.Agenda.Agenda;
import PIIA.Plante.Plante;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Meteo extends BorderPane {
    private final Agenda agenda;
    private Plante plante;
    private final VBox left;

    public Meteo(VBox left, Plante plante, Agenda agenda) {
        this.left = left;
        this.setLeft(left);
        this.plante = plante;
        this.agenda = agenda;
        setButtonActions();
    }

    private void setButtonActions() {
        left.getChildren().get(0).setOnMouseClicked(mouseEvent -> getScene().setRoot(agenda));
        left.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }
}
