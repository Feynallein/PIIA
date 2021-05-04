package PIIA.PopUp;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.Flow;

public class PromptPopUp extends PopUpPane {
    private final String text;

    public PromptPopUp(String text) {
        super();
        this.text = text;
        display();
        setHgap(10);
        setVgap(30);
    }

    @Override
    void display() {
        FlowPane msg = new FlowPane();
        msg.setAlignment(Pos.CENTER);
        msg.getChildren().add(new Text(text));
        add(msg, 0, 1);

        Button b = new Button("Ok");
        b.setOnMouseClicked(mouseEvent -> ((Stage) b.getScene().getWindow()).close());

        FlowPane fp = new FlowPane();
        fp.setAlignment(Pos.CENTER);
        fp.getChildren().add(b);
        add(fp, 0, 2);
    }
}
