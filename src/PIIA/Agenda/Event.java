package PIIA.Agenda;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public class Event {
    private final Color color;
    private final LocalDate date;
    private final int startingTime;
    private final int endingTime;
    private final String label;

    public Event(Color color, LocalDate date, int startingTime, int endingTime, String label) {
        this.color = color;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.label = label;
    }

    public Event(Color color, LocalDate date, int startingTime, String label) {
        this.color = color;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = startingTime + 1;
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public LocalDate getDay() {
        return date;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public int getEndingTime() {
        return endingTime;
    }

    public String getLabel(){
        return label;
    }
}
