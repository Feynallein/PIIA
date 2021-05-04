package PIIA.Plante;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Overlay extends BorderPane {
    public Overlay(int x, int y, int width, int height) {
        double alpha = 0.8;
        Color on = new Color(0, 0, 0, alpha);
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(on);
        setCenter(rect);
    }
}
