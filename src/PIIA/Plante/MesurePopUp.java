package PIIA.Plante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MesurePopUp extends GridPane {
    private FichePlante fp;

    public MesurePopUp(FichePlante fp) {
        this.fp = fp;
        display();
    }

    private void display() {
        /* Mesure */
        setHgap(10);
        Text nameT = new Text("Nom de la mesure :");
        add(nameT, 0, 0);

        TextField nameTF = new TextField();
        add(nameTF, 1, 0);

        TextField valeurTF = new TextField();
        add(valeurTF,2,0);

        //Bouton de tri de la liste
        ObservableList<String> unites =
                FXCollections.observableArrayList(

                );
        final ComboBox comboBox = new ComboBox(unites);
        comboBox.setEditable(true);
        comboBox.getItems().addAll();
        add(comboBox, 3,0);

        /* Done !*/
        Button b = new Button("Done !");
        b.setOnMouseClicked(mouseEvent -> {

                //plante.createPlante(nameTF.getText());
                fp.ajouterMesure(nameTF.toString(),valeurTF.toString(),comboBox.getId(),fp.getFenetre());
                ((Stage) b.getScene().getWindow()).close();
            
        });
        add(b, 0, 9);
    }
}
