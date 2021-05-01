package PIIA;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.awt.*;

public class Overlay extends BorderPane {
    private boolean display;
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle rect;


    public Overlay(int x,int y,int width,int height){
        double alpha = 0.7; // 50% transparent
        Color on = new Color(0, 0, 0, alpha);
        this.rect = new Rectangle(x,y,width,height);
        this.display = false;
        rect.setFill(on);
        setCenter(rect);
    }

    public Overlay getOverlay(){return this;}


    public boolean isDisplayed() {
        return display;
    }

    public void switchDisplay(){
        if (this.display)
            display = false;
        else
            display = true;
    }
    @Override
    public Node getStyleableNode() {
        return null;
    }
}
