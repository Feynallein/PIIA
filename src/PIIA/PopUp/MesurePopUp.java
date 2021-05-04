package PIIA.PopUp;

import PIIA.Plante.FichePlante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MesurePopUp extends PopUpPane {
    private FichePlante fp;
    private  VBox vb;
    private Button b;
    private boolean action;

    public MesurePopUp(FichePlante fp, VBox vb, Button b, boolean action) {
        super();
        this.fp = fp;
        this.vb = vb;
        this.b = b;
        this.action = action;
        display();
    }

    public MesurePopUp(FichePlante fp) {
        super();
        this.fp = fp;
        display2();
    }


    @Override
    void display() {
        /* Mesure */
        Text nameT = new Text("Valeur  :");
        add(nameT, 0, 0);

        TextField valeurTF = new TextField();
        add(valeurTF, 1, 0);

        Text uniteT = new Text("Unite  :");
        add(uniteT, 0, 1);

        TextField uniteTF = new TextField();
        add(uniteTF,1,1);

        /* Done !*/
        Button done = new Button("Done !");
        done.setOnMouseClicked(mouseEvent -> {
                if (action)
                    fp.ajouterValeur(this.vb,valeurTF.getText(),uniteTF.getText(),b);
                else
                    fp.modifierValeur(this.vb,valeurTF.getText(),uniteTF.getText(),b);
                ((Stage) done.getScene().getWindow()).close();
            
        });
        add(done, 0, 9);
    }

    void display2() {
        /* Mesure */
        Text nameT = new Text("Nom de la valeur  :");
        add(nameT, 0, 0);

        TextField nameTF = new TextField();
        add(nameTF, 1, 0);

        /* Done !*/
        Button done = new Button("Done !");
        done.setOnMouseClicked(mouseEvent -> {
            fp.ajouterMesure();
            ((Stage) done.getScene().getWindow()).close();

        });
        add(done, 0, 9);
    }
}
