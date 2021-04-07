import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Button b1 = new Button("agenda");
        b1.setPrefSize(225, 50);
        Button b2 = new Button("plantedex");
        b2.setPrefSize(225, 50);
        Button b3 = new Button("meteo");
        b3.setPrefSize(225, 50);
        return new VBox(b1, b2, b3);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
