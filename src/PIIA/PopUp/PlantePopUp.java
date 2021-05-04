package PIIA.PopUp;

import PIIA.Plante.Plante;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlantePopUp extends PopUpPane {
    private final Plante plante;

    public PlantePopUp(Plante plante) {
        this.plante = plante;
        display();
    }

    @Override
    void display() {
        /* Plante */
        Text nameT = new Text("Nom de la plante :");
        add(nameT, 0, 0);

        TextField nameTF = new TextField();
        add(nameTF, 1, 0);

        /* Done !*/
        Button b = new Button("Done !");
        b.setOnMouseClicked(mouseEvent -> {
            if (!nameTF.getText().equals("")) {
                plante.createPlante(nameTF.getText());
                ((Stage) b.getScene().getWindow()).close();
                new PopUp(plante.getAgenda().getStage(), new PromptPopUp("Plante ajoutée !"), "Confirmation");
            }
        });
        add(b, 0, 9);
    }
}
