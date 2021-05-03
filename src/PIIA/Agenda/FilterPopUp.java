package PIIA.Agenda;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FilterPopUp extends GridPane {
    private final Agenda agenda;

    public FilterPopUp(Agenda agenda) {
        this.agenda = agenda;
        display();
    }

    private void display() {
        /* Label */
        Text labelT = new Text("Filter's name :");
        add(labelT, 0, 0);

        TextField labelTF = new TextField();
        add(labelTF, 1, 0);

        /* Color */
        Text colorLT = new Text("Color :");
        add(colorLT, 0, 2);

        ColorPicker colorPicker = new ColorPicker();
        //todo: proposition : mettre un couleur aléatoire non deja prise par un filtre existant?
        add(colorPicker, 1, 3);

        Rectangle rectangle = new Rectangle(50, 25);
        rectangle.setFill(colorPicker.getValue());
        add(rectangle, 0, 3);

        colorPicker.setOnAction(actionEvent -> rectangle.setFill(colorPicker.getValue()));

        /* Done !*/
        Button b = new Button("Ok");
        b.setOnMouseClicked(mouseEvent -> {
            if (!labelTF.getText().equals("")) {
                agenda.createNewFilter(new Filter(labelTF.getText(), colorPicker.getValue()));
                ((Stage) b.getScene().getWindow()).close();
            }
        });
        add(b, 0, 9);
    }
}

