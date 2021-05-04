package PIIA;

import PIIA.Agenda.Agenda;
import PIIA.Plante.Plante;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public final static int WIDTH = 1366;
    public final static int HEIGHT = 768;

    @Override
    public void start(Stage stage) throws Exception {
        Agenda a = new Agenda(createLeftMenu(), stage);
        Plante p = new Plante(createLeftMenu(), a);
        Meteo m = new Meteo(createLeftMenu(), p, a);

        a.setPlante(p);
        a.setMeteo(m);
        p.setMeteo(m);

        // 16:9
        Scene scene = new Scene(a, WIDTH, HEIGHT);
        stage.setTitle("Agenda Horticulture");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createLeftMenu(){
        Button b1 = new Button("agenda");
        b1.setPrefSize(225, 50);
        b1.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
        b1.setTextFill(Color.WHITE);
        b1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Button b2 = new Button("plantedex");
        b2.setPrefSize(225, 50);
        b2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        b2.setTextFill(Color.BLACK);
        b2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Button b3 = new Button("meteo");
        b3.setPrefSize(225, 50);
        b3.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        b3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        b3.setTextFill(Color.BLACK);

        return new VBox(b1, b2, b3);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
