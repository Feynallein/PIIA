package PIIA.Agenda;

import PIIA.Main;
import PIIA.Meteo;
import PIIA.Plante.Plante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private final ArrayList<VBox> days = new ArrayList<>();
    private final DayOfWeek[] week = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Filter> filters = new ArrayList<>();
    private final Stage stage;

    public Agenda(VBox left, final Stage stage) {
        //temp
        filters.add(new Filter("None", Color.WHITE));//a garder!!
        filters.add(new Filter("School", Color.GREEN));
        filters.add(new Filter("Work", Color.LIGHTCYAN));
        events.add(new Event(filters.get(1), LocalDate.now().with(week[0]), 12, "KFC"));
        events.add(new Event(filters.get(2), LocalDate.now().with(week[5]), 12, 16, "KFC"));

        this.stage = stage;
        this.left = left;
        this.setLeft(left);
        setButtonActions();
        littleAgenda();
        bigAgenda();
    }

    private void bigAgenda(){
        VBox names = new VBox();
        for(int i = -1; i < 24; i++){
            Text txt;
            if(i == -1) {
                txt = new Text("");
                txt.setFont(new Font(7));
            }

            else {
                txt = new Text(i + ":00");
                txt.setFont(new Font(23));
            }

            names.getChildren().add(txt);
        }

        days.add(names);

        for(int i = 0; i < 7; i++){
            VBox box = new VBox();
            Cell dayCell = new Cell(stage, this, filters);

            /* Adding the name of the week */
            dayCell.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth())/7, Main.HEIGHT/25f);
            dayCell.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now().with(week[i])));
            box.getChildren().add(dayCell);

            /* Adding other cells */
            for(int j = 0; j < 24; j++){
                Cell cell = new Cell(LocalDate.now().with(week[i]), j, stage, this, filters, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now())
                        .equals(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now().with(week[i]))));
                cell.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth())/7, Main.HEIGHT/25f);

                for(Event e : events){
                    if(e.getDay().compareTo(LocalDate.now().with(week[i])) == 0 && e.getStartingTime() <= j && e.getEndingTime() > j){
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

    public void createNewEvent(Event e){
        this.events.add(e);
        center = new HBox();
        days.clear();
        bigAgenda();
    }

    private void littleAgenda(){
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        left.getChildren().add(datePickerSkin.getPopupContent());
    }

    private void setButtonActions(){
        left.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
        left.getChildren().get(2).setOnMouseClicked(mouseEvent -> getScene().setRoot(meteo));
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }
}
