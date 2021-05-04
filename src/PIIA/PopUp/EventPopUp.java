package PIIA.PopUp;

import PIIA.Agenda.Agenda;
import PIIA.Agenda.Event;
import PIIA.Agenda.Filter;
import PIIA.Plante.FichePlante;
import PIIA.Plante.Plante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventPopUp extends PopUpPane {
    private final Agenda agenda;
    private final int plantIdx;
    private final LocalDate date;
    private int startingTime;
    private final String name;
    private final ArrayList<Filter> filters;

    public EventPopUp(Agenda agenda, LocalDate date, int startingTime, ArrayList<Filter> filters) {
        super();
        this.agenda = agenda;
        this.date = date;
        this.startingTime = startingTime;
        this.name = "";
        this.filters = filters;
        plantIdx = 0;
        display();
    }

    public EventPopUp(Agenda agenda, LocalDate date, ArrayList<Filter> filters, int idx) {
        super();
        this.agenda = agenda;
        this.date = date;
        this.filters = filters;
        this.startingTime = 0;
        this.name = "";
        this.plantIdx = idx + 1;
        display();
    }

    public EventPopUp(Agenda agenda, LocalDate date, String name, ArrayList<Filter> filters, int idx) {
        super();
        this.agenda = agenda;
        this.plantIdx = idx + 1;
        this.date = date;
        this.name = name;
        this.filters = filters;
        this.startingTime = 0;
        display();
    }

    @Override
    void display() {
        int yPos = 0;

        /* Label */
        Text labelT = new Text("Event's name :");
        add(labelT, 0, yPos);

        TextField labelTF = new TextField(name);
        if (!name.equals("")) labelTF.setDisable(true);
        add(labelTF, 1, yPos);

        yPos++;

        /* Date */
        Text dateT = new Text("Date :");
        add(dateT, 0, yPos);

        TextField dateTF = new TextField(date.toString());
        dateTF.setDisable(true);
        add(dateTF, 1, yPos);

        yPos++;

        /* Starting time */
        Text startingTimeT = new Text("Starting time :");
        add(startingTimeT, 0, yPos);

        if (startingTime == 0) {
            ObservableList<String> array = FXCollections.observableArrayList();
            for (int i = 0; i < 24; i++) {
                array.add(i + ":00");
            }
            ComboBox<String> startingTimeCB = new ComboBox<>(array);
            startingTimeCB.setValue(array.get(0));
            startingTimeCB.setVisibleRowCount(7);
            add(startingTimeCB, 1, yPos);
            String[] splits = startingTimeCB.getValue().split(":");
            startingTime = Integer.parseInt(splits[0]);
            System.out.println(startingTime);
        } else {
            TextField startingTimeTF = new TextField(startingTime + ":00");
            startingTimeTF.setDisable(true);
            add(startingTimeTF, 1, yPos);
        }

        yPos++;

        /* Ending time */
        Text endingTimeT = new Text("Ending time : ");
        add(endingTimeT, 0, yPos);

        ObservableList<String> arr = FXCollections.observableArrayList();
        for (int i = startingTime + 1; i < 24; i++) {
            arr.add(i + ":00");
        }
        ComboBox<String> endingTimeCB = new ComboBox<>(arr);
        endingTimeCB.setValue(arr.get(0));
        endingTimeCB.setVisibleRowCount(7);
        add(endingTimeCB, 1, yPos);

        yPos++;

        /* Filters */
        Text filtersT = new Text("Filter :");
        add(filtersT, 0, yPos);

        ObservableList<String> filtersArr = FXCollections.observableArrayList();
        for (Filter f : filters) {
            filtersArr.add(f.getName());
        }

        ComboBox<String> filtersCb = new ComboBox<>(filtersArr);
        filtersCb.setValue(filtersArr.get(0));
        filtersCb.setVisibleRowCount(yPos);
        add(filtersCb, 1, 4);

        yPos++;

        /* Recurrence */
        Text recurrenceT = new Text("Recurrence :");
        add(recurrenceT, 0, yPos);

        ObservableList<String> recurrences = FXCollections.observableArrayList();
        recurrences.add("Never");
        recurrences.add("Daily");
        recurrences.add("Weekly");
        recurrences.add("Monthly");
        recurrences.add("Yearly");

        ComboBox<String> recurrenceCB = new ComboBox<>(recurrences);
        recurrenceCB.setValue(recurrences.get(0));
        add(recurrenceCB, 1, yPos);

        yPos++;

        /* Plant */
        Text plantT = new Text("Plant :");
        add(plantT, 0, yPos);

        ObservableList<String> plants = FXCollections.observableArrayList();
        plants.add("");
        for (FichePlante fp : Plante.getPlantes()) {
            plants.add(fp.getNom());
        }

        ComboBox<String> plantCB = new ComboBox<>(plants);
        plantCB.setValue(plants.get(plantIdx));
        add(plantCB, 1, yPos);

        yPos++;

        /* Done !*/
        Button b = new Button("Ok");
        int finalStartingTime = startingTime;
        b.setOnMouseClicked(mouseEvent -> {
            Filter selectedFilter = null;
            for (Filter f : filters) {
                if (f.getName().equals(filtersCb.getValue())) {
                    selectedFilter = f;
                    break;
                }
            }
            String[] splits = endingTimeCB.getValue().split(":");
            agenda.createNewEvent(new Event(selectedFilter, date, finalStartingTime, Integer.parseInt(splits[0]), labelTF.getText(), plantCB.getValue()));
            ((Stage) b.getScene().getWindow()).close();
            new PopUp(agenda.getStage(), new PromptPopUp("Event created!"), "Confirmation");
        });
        add(b, 0, yPos);
    }
}
