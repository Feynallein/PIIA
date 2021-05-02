package PIIA;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.awt.*;

public class Overlay extends BorderPane {
    private Rectangle rect;

    public Overlay(int x,int y,int width,int height){
        double alpha = 0.8;
        Color on = new Color(0, 0, 0, alpha);
        this.rect = new Rectangle(x,y,width,height);
        rect.setFill(on);
        setCenter(rect);
    }

}
