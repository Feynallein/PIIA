package PIIA.Agenda;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Cell extends Region {
    private boolean hasText;

    public Cell() {
        this.hasText = false;
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        updateOnClick();
    }

    public Cell(boolean currentWeek) {
        this.hasText = false;
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        if(currentWeek) setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        updateOnClick();
    }

    public void setText(String text){
        hasText = true;
        updateOnClick();
        StackPane pane = new StackPane(new Text(text));
        getChildren().setAll(pane);
    }

    public void addEvent(Event e, boolean hasLabel){
        setBackground(new Background(new BackgroundFill(e.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        if(hasLabel) setText(e.getLabel());
    }

    private void updateOnClick(){
        //todo: create new event if no event already
        //if(!this.hasText) setOnMouseClicked(mouseEvent -> setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))));
        //else setOnMouseClicked(null);
    }
}
