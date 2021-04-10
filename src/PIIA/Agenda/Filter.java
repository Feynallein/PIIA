package PIIA.Agenda;

import javafx.scene.paint.Color;

public class Filter {
    private String name;
    private Color color;

    public Filter(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
