package PIIA.Plante;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class MyButtonSkin extends ButtonSkin {

    public MyButtonSkin(Button b) {
        super(b);

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(b);
        fadeIn.setToValue(1);
        b.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(b);
        fadeOut.setToValue(0.6);
        b.setOnMouseExited(e -> fadeOut.playFromStart());

        b.setOpacity(0.6);
    }

}