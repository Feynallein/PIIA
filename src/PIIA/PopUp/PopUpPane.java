package PIIA.PopUp;

import javafx.scene.layout.GridPane;

public abstract class PopUpPane extends GridPane {
    public PopUpPane(){
        this.setHgap(10);
        this.setVgap(3);
    }

    abstract void display();
}
