package PIIA.PopUp;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PromptPopUp extends PopUpPane {
    private final String text;

    public PromptPopUp(String text) {
        super();
        this.text = text;
        display();
    }

    @Override
    void display() {
        add(new Text(text), 0, 0);

        Button b = new Button("Ok");
        b.setOnMouseClicked(mouseEvent -> ((Stage) b.getScene().getWindow()).close());
        add(b, 0, 1);
    }
}
