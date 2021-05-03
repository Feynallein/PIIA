package PIIA.Agenda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class FilterPopUp extends GridPane {
    private Agenda agenda;

    public FilterPopUp(Agenda agenda) {
        this.agenda = agenda;
        display();
    }

    private void display() {
        /* Label */
        Text labelT = new Text("Label : ");
        add(labelT, 0, 0);

        TextField labelTF = new TextField();
        add(labelTF, 1, 0);

        /* Color */
        ColorPicker colorPicker = new ColorPicker();
        //faire que ca choisi une coulor random non présente
        add(colorPicker, 1, 2);

        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(colorPicker.getValue());
        add(rectangle, 0, 2);

        colorPicker.setOnAction(actionEvent -> rectangle.setFill(colorPicker.getValue()));



        /* Done !*/
        Button b = new Button("Done !");
        b.setOnMouseClicked(mouseEvent -> {
            if(!labelTF.getText().equals("")) {
                agenda.createNewFilter(new Filter(labelTF.getText(), colorPicker.getValue()));
                ((Stage) b.getScene().getWindow()).close();
            }
        });
        add(b, 0, 9);
    }
}

