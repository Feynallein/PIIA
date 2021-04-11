package PIIA.Plante;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
            plante.createPlante(nameTF.getText());
            ((Stage) b.getScene().getWindow()).close();
        });
        add(b, 0, 9);
    }
}
