package PIIA.Agenda;

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

public class EventPopUp extends GridPane {
    private final Agenda agenda;

    public EventPopUp(Agenda agenda, LocalDate date, int startingTime, ArrayList<Filter> filters) {
        this.agenda = agenda;
        display(date, startingTime, filters);
    }

    private void display(LocalDate date, int startingTime, ArrayList<Filter> filters) {
        /* Label */
        Text labelT = new Text("Event's name :");
        add(labelT, 0, 0);

        TextField labelTF = new TextField();
        add(labelTF, 1, 0);

        /* Date */
        Text dateT = new Text("Date :");
        add(dateT, 0, 1);

        TextField dateTF = new TextField(date.toString());
        dateTF.setDisable(true);
        add(dateTF, 1, 1);

        /* Starting time */
        Text startingTimeT = new Text("Starting time :");
        add(startingTimeT, 0, 2);

        TextField startingTimeTF = new TextField(startingTime + ":00");
        startingTimeTF.setDisable(true);
        add(startingTimeTF, 1, 2);

        /* Ending time */
        Text endingTimeT = new Text("Ending time : ");
        add(endingTimeT, 0, 4);

        ObservableList<String> arr = FXCollections.observableArrayList();
        for (int i = startingTime + 1; i < 24; i++) {
            arr.add(i + ":00");
        }
        ComboBox<String> endingTimeBc = new ComboBox<>(arr);
        endingTimeBc.setValue(arr.get(0));
        endingTimeBc.setVisibleRowCount(7);
        add(endingTimeBc, 1, 4);

        /* Filters */
        Text filtersT = new Text("Filter :");
        add(filtersT, 0, 8);

        ObservableList<String> filtersArr = FXCollections.observableArrayList();
        for (Filter f : filters) {
            filtersArr.add(f.getName());
        }
        ComboBox<String> filtersCb = new ComboBox<>(filtersArr);
        filtersCb.setValue(filtersArr.get(0));
        filtersCb.setVisibleRowCount(5);
        add(filtersCb, 1, 8);

        /* Done !*/
        Button b = new Button("Ok");
        b.setOnMouseClicked(mouseEvent -> {
            Filter selectedFilter = null;
            for (Filter f : filters) {
                if (f.getName().equals(filtersCb.getValue())) {
                    selectedFilter = f;
                    break;
                }
            }
            String[] splits = endingTimeBc.getValue().split(":");
            agenda.createNewEvent(new Event(selectedFilter, date, startingTime, Integer.parseInt(splits[0]), labelTF.getText()));
            ((Stage) b.getScene().getWindow()).close();
        });
        add(b, 0, 9);
    }
}
