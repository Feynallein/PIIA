package PIIA.Agenda;

import PIIA.Main;
import PIIA.Meteo;
import PIIA.Plante.Plante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Agenda extends BorderPane {
    private Plante plante;
    private Meteo meteo;
    private final VBox left;
    private HBox center = new HBox();
    private DatePicker datePicker = new DatePicker(LocalDate.now());
    private final ArrayList<VBox> days = new ArrayList<>();
    private final DayOfWeek[] week = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Filter> filters = new ArrayList<>();
    private final Stage stage;

    public Agenda(VBox left, final Stage stage) {
        filters.add(new Filter("None", Color.WHITE)); // Always here

        /* Examples of filters & events */
        filters.add(new Filter("School", Color.GREEN));
        filters.add(new Filter("Work", Color.BLUEVIOLET));
        events.add(new Event(filters.get(1), LocalDate.now().with(week[0]), 12, "Event 1", ""));
        events.add(new Event(filters.get(2), LocalDate.now().with(week[5]), 12, 16, "Event 2", "plante au pif"));
        events.add(new Event(filters.get(2), LocalDate.of(2021, 5, 12), 12, 16, "Event 3", ""));

        this.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30), CornerRadii.EMPTY, Insets.EMPTY)));
        this.left = left;
        this.stage = stage;
        this.setLeft(left);
        setButtonActions();
        littleAgenda();
        filterButton();
        checkBoxes();
        bigAgenda();
    }

    private void filterButton() {
        Button button = new Button("Ajouter un filtre");
        button.setPrefSize(225, 50);
        button.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        button.setTextFill(Color.WHITE);
        button.setOnMouseClicked(mouseEvent -> {
            final Stage eventPopUp = new Stage();
            eventPopUp.setTitle("Filter Creator");
            eventPopUp.initModality(Modality.APPLICATION_MODAL);
            eventPopUp.initOwner(stage);
            FilterPopUp popUp = new FilterPopUp(this);
            Scene popUpScene = new Scene(popUp);
            eventPopUp.setScene(popUpScene);
            eventPopUp.show();
        });
        left.getChildren().add(button);
    }

    private void checkBoxes() {
        VBox box = new VBox();
        for (Filter f : filters) {
            CheckBox checkBox = new CheckBox(f.getName());
            checkBox.setTextFill(f.getColor());
            checkBox.setSelected(f.isTicked());
            checkBox.setPrefWidth(left.getPrefWidth());
            checkBox.setOnMouseClicked(mouseEvent -> {
                if(checkBox.isSelected()) f.tick();
                else f.unTick();
                center = new HBox();
                days.clear();
                bigAgenda();
            });
            box.getChildren().add(checkBox);
        }
        left.getChildren().add(box);
    }

    private boolean checkIfFilterIsTicked(Filter filter){
        for(Filter f : filters){
            if(f == filter && f.isTicked()) return true;
        }
        return false;
    }

    private void bigAgenda() {
        VBox names = new VBox();
        for (int i = -1; i < 24; i++) {
            Text txt;
            if (i == -1) {
                txt = new Text("");
                txt.setFont(new Font(7));
            } else {
                txt = new Text(i + ":00");
                txt.setFont(new Font(23));
            }
            txt.setFill(Color.WHITE);
            names.getChildren().add(txt);
        }

        days.add(names);

        for (int i = 0; i < 7; i++) {
            VBox box = new VBox();
            Cell dayCell = new Cell(stage, this, filters, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now())
                    .equals(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(datePicker.getValue().with(week[i]))));

            /* Adding the name of the week */
            dayCell.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth()) / 7, Main.HEIGHT / 25f);
            dayCell.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(datePicker.getValue().with(week[i])), "");
            box.getChildren().add(dayCell);

            /* Adding other cells */
            for (int j = 0; j < 24; j++) {
                Cell cell = new Cell(LocalDate.now().with(week[i]), j, stage, this, filters, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now())
                        .equals(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(datePicker.getValue().with(week[i]))));
                cell.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth()) / 7, Main.HEIGHT / 25f);

                /* Displaying the event if there is one */
                for (Event e : events) {
                    if (e.getDay().compareTo(datePicker.getValue().with(week[i])) == 0 && e.getStartingTime() <= j && e.getEndingTime() > j && checkIfFilterIsTicked(e.getFilter())) {
                        cell.addEvent(e, e.getStartingTime() == j);
                    }
                }
                box.getChildren().add(cell);
            }
            days.add(box);
        }
        center.getChildren().addAll(days);
        setCenter(center);
    }

    public void createNewFilter(Filter f) {
        filters.add(f);
        left.getChildren().remove(5);
        checkBoxes();
    }

    public void createNewEvent(Event e) {
        this.events.add(e);
        center = new HBox();
        days.clear();
        bigAgenda();
    }

    private void littleAgenda() {
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        datePickerSkin.getPopupContent().setOnMouseClicked(mouseEvent -> {
            center = new HBox();
            days.clear();
            bigAgenda();
        });
        left.getChildren().add(datePickerSkin.getPopupContent());
    }

    private void setButtonActions() {
        left.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
        left.getChildren().get(2).setOnMouseClicked(mouseEvent -> getScene().setRoot(meteo));
    }

    public ArrayList<Filter> getFilters(){
        return filters;
    }

    public Stage getStage() {
        return stage;
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }
}
