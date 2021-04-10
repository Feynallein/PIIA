package PIIA.Plante;

import PIIA.Agenda.Agenda;
import PIIA.Agenda.Event;
import PIIA.Agenda.Filter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class PlantePopUp extends GridPane {
    private Plante plante;

    public PlantePopUp(Plante plante) {
        this.plante = plante;
        display();
    }

    private void display() {
        /* Plante */
        Text nameT = new Text("Nom de la plante :");
        add(nameT, 0, 0);

        TextField nameTF = new TextField();
        add(nameTF, 1, 0);

        /* Done !*/
        Button b = new Button("Done !");
        b.setOnMouseClicked(mouseEvent -> {
            plante.addPlante(nameTF.getText());
            ((Stage) b.getScene().getWindow()).close();
        });
        add(b, 0, 9);
    }
}
