package PIIA.Agenda;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;


public class Cell extends Region {
    private LocalDate date;
    private int startingTime;
    private boolean hasEvent;
    private final Stage stage;
    private final Agenda agenda;
    private final ArrayList<Filter> filters;

    public Cell(Stage stage, Agenda agenda, ArrayList<Filter> filters, boolean currentDay) {
        this.hasEvent = false;
        setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.stage = stage;
        this.agenda = agenda;
        this.filters = filters;
        if (currentDay)
            setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        updateOnClick();
    }

    public Cell(LocalDate date, int startingTime, Stage stage, Agenda agenda, ArrayList<Filter> filters, boolean currentDay) {
        this.hasEvent = false;
        setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.date = date;
        this.startingTime = startingTime;
        this.stage = stage;
        this.agenda = agenda;
        this.filters = filters;
        if (currentDay)
            setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        updateOnClick();
    }

    public void setTime(int time) {
        this.startingTime = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setText(String text, String plant) {
        this.hasEvent = true;
        updateOnClick();
        Text txt = new Text(text);
        txt.setFill(Color.WHITE);
        VBox box = new VBox(txt);
        if(!plant.equals("")) box.getChildren().add(new Text(plant));
        getChildren().setAll(box);
    }

    public void addEvent(Event e, boolean hasLabel) {
        setBackground(new Background(new BackgroundFill(e.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        if (hasLabel) setText(e.getLabel(), e.getPlantName());
    }

    private void updateOnClick() {
        if (!this.hasEvent) setOnMouseClicked(mouseEvent -> {
            final Stage eventPopUp = new Stage();
            eventPopUp.setTitle("Event Creator");
            eventPopUp.initModality(Modality.APPLICATION_MODAL);
            eventPopUp.initOwner(stage);
            EventPopUp popUp = new EventPopUp(agenda, date, startingTime, filters);
            Scene popUpScene = new Scene(popUp);
            eventPopUp.setScene(popUpScene);
            eventPopUp.show();
        });
        else setOnMouseClicked(null);
    }
}
