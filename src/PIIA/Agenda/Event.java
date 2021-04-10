package PIIA.Agenda;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public class Event {
    private final LocalDate date;
    private final int startingTime;
    private final int endingTime;
    private final String label;
    private Filter filter;

    public Event(Filter filter, LocalDate date, int startingTime, int endingTime, String label) {
        this.filter = filter;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.label = label;
    }

    public Event(Filter filter, LocalDate date, int startingTime, String label) {
        this.filter = filter;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = startingTime + 1;
        this.label = label;
    }

    public Color getColor() {
        return filter.getColor();
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
