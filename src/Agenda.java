import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Agenda extends BorderPane {
    private Plante plante;
    private Meteo meteo;
    private final VBox left;
    private final HBox center = new HBox();
    private final ArrayList<VBox> days = new ArrayList<>();
    private final DayOfWeek[] week = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};

    public Agenda(VBox left) {
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
                txt = new Text(5, Main.HEIGHT / 25f, "");
            }

            else {
                txt = new Text(5, Main.HEIGHT/25f,i + ":00");
            }

            txt.setFont(new Font(23));
            names.getChildren().add(txt);
        }
        days.add(names);
        for(int i = 0; i < 7; i++){
            VBox box = new VBox();
            Button b = new Button();
            b.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth())/7, Main.HEIGHT/25f);
            b.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(LocalDate.now().with(week[i])));
            box.getChildren().add(b);
            for(int j = 0; j < 24; j++){
                Button time = new Button();
                time.setPrefSize((Main.WIDTH - left.getPrefWidth() - names.getPrefWidth())/7, Main.HEIGHT/25f);
                box.getChildren().add(time);
            }
            days.add(box);
        }

        for(VBox vb : days){
            center.getChildren().add(vb);
        }

        setCenter(center);
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
