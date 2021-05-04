package PIIA.Plante;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TransparentButton extends ButtonSkin {
    public TransparentButton(Button b) {
        super(b);

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(b);
        fadeIn.setToValue(1);
        b.setOnMouseEntered(e -> {
            fadeIn.playFromStart();
            b.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        });

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(b);
        fadeOut.setToValue(0.6);
        b.setOnMouseExited(e -> {
            fadeOut.playFromStart();
            b.setBorder(null);
        });

        b.setOpacity(0.6);
    }
}