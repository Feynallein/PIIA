package PIIA.Agenda;

import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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

    public Cell(Stage stage, Agenda agenda, ArrayList<Filter> filters) {
        this.hasEvent = false;
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.stage = stage;
        this.agenda = agenda;
        this.filters = filters;
        updateOnClick();
    }

    public Cell(LocalDate date, int startingTime, Stage stage, Agenda agenda, ArrayList<Filter> filters, boolean currentWeek) {
        this.hasEvent = false;
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.date = date;
        this.startingTime = startingTime;
        this.stage = stage;
        this.agenda = agenda;
        this.filters = filters;
        if(currentWeek) setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        updateOnClick();
    }

    public void setTime(int time){
        this.startingTime = time;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setText(String text){
        this.hasEvent = true;
        updateOnClick();
        StackPane pane = new StackPane(new Text(text));
        getChildren().setAll(pane);
    }

    public void addEvent(Event e, boolean hasLabel){
        setBackground(new Background(new BackgroundFill(e.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        if(hasLabel) setText(e.getLabel());
    }

    private void updateOnClick(){
        if(!this.hasEvent) setOnMouseClicked(mouseEvent -> {
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
