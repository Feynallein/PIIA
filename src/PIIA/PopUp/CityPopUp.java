package PIIA.PopUp;

import PIIA.Agenda.Agenda;
import PIIA.Weather.Weather;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CityPopUp extends PopUpPane {
    private final Weather weather;
    private final Agenda agenda;

    public CityPopUp(Agenda agenda, Weather weather) {
        super();
        this.agenda = agenda;
        this.weather = weather;
        display();
    }

    @Override
    void display() {
        /* City's name */
        Text labelT = new Text("Nom de la ville :");
        add(labelT, 0, 0);

        TextField labelTF = new TextField();
        add(labelTF, 1, 0);

        /* Done !*/
        Button b = new Button("Ok");
        b.setOnMouseClicked(mouseEvent -> {
            weather.changeCity(labelTF.getText());
            ((Stage) b.getScene().getWindow()).close();
            new PopUp(agenda.getStage(), new PromptPopUp("Ville changée!"), "Confirmation");
        });
        add(b, 0, 1);
    }
}
