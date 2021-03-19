import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public final static int WIDTH = 1366;
    public final static int HEIGHT = 768;

    @Override
    public void start(Stage stage) throws Exception {
        Agenda a = new Agenda(createLeftMenu());
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
        Button b1 = new Button("", new ImageView("/plante.jpg"));
        b1.setPrefHeight(HEIGHT/3.);
        b1.setMaxWidth(50);

        Button b2 = new Button("", new ImageView("/plante.jpg"));
        b2.setPrefHeight(HEIGHT/3.);
        b2.setMaxWidth(50.);

        Button b3 = new Button("", new ImageView("/plante.jpg"));
        b3.setPrefHeight(HEIGHT/3.);
        b3.setMaxWidth(50);
        return new VBox(b1, b2, b3);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
