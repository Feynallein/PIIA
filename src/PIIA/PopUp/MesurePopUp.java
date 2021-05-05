package PIIA.PopUp;

import PIIA.Plante.FichePlante;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MesurePopUp extends PopUpPane {
    private final FichePlante fp;
    private VBox vb;
    private final Button b;
    private boolean action;

    public MesurePopUp(FichePlante fp, VBox vb, Button b, boolean action) {
        super();
        this.fp = fp;
        this.vb = vb;
        this.b = b;
        this.action = action;
        display();
    }

    public MesurePopUp(FichePlante fp, Button b) {
        super();
        this.fp = fp;
        this.b = b;
        displayNom();
    }


    @Override
    void display() {
        /* Mesure */
        Text nameT = new Text("Valeur : ");
        add(nameT, 0, 0);

        TextField valeurTF = new TextField();
        add(valeurTF, 1, 0);

        Button plan = new Button("Selectionner une date");
        plan.setOnMouseClicked(mouseEvent -> {
        });
        add(plan, 0, 3);

        /* Done !*/
        Button done = new Button("Ok");
        done.setOnMouseClicked(mouseEvent -> {
            if (action)
                fp.ajouterValeur(this.vb, valeurTF.getText(), b);
            else
                fp.modifierValeur(this.vb, valeurTF.getText(), b);
            ((Stage) done.getScene().getWindow()).close();

        });
        add(done, 0, 9);
    }

    void displayNom() {
        /* Mesure */
        Text nameT = new Text(" Nom de la valeur  :");
        add(nameT, 0, 0);

        TextField valeurTF = new TextField();
        add(valeurTF, 1, 0);

        Text uniteT = new Text("Unité  :");
        add(uniteT, 0, 1);

        TextField uniteTF = new TextField();
        add(uniteTF, 1, 1);

        /* Done !*/
        Button done = new Button("Ok");
        done.setOnMouseClicked(mouseEvent -> {
            fp.choisirNom(valeurTF.getText(), uniteTF.getText(), b);
            ((Stage) done.getScene().getWindow()).close();

        });
        add(done, 0, 9);
    }

}
