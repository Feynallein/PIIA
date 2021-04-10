package PIIA.Plante;

import PIIA.Agenda.Agenda;
import PIIA.Main;
import PIIA.Meteo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Plante extends BorderPane {
    private final VBox left;
    private final VBox plantList = new VBox();
    private ImageView plante;
    private ArrayList<Image> preview = new ArrayList<>();
    private ArrayList<String> plantes = new ArrayList<>();
    private final ArrayList<VBox> noms = new ArrayList<>();
    private ScrollPane scroll = new ScrollPane();

    private Agenda agenda;
    private Meteo meteo;

    public Plante(VBox left, Agenda agenda) {
        this.left = left;
        this.setLeft(left);
        this.agenda = agenda;
        setButtonActions();

        //Remplissage de la liste
        addImagePlante("abelia.jpg");
        addImagePlante("example.jpg");
        addImagePlante("lychnis.jpg");
        addImagePlante("Macchia.jpg");
        addImagePlante("plante.jpg");
        addImagePlante("sedum.jpg");
        addImagePlante("silene.jpg");

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT/10f) * 7);
        listPlante();
        previewPlante();
        setAgenda(agenda);
    }


    private void previewPlante(){
        GridPane gb = new GridPane();
        gb.setHgap(50);
        gb.setVgap(50);
        FlowPane layout = new FlowPane();
        plante = new ImageView(("example.jpg"));
        plante.setFitHeight(500);
        plante.setFitWidth(400);
        layout.getChildren().add(plante);
        gb.add(layout, 2,2);
        setCenter(gb);

        System.out.println(plantes);
    }

    private void addImagePlante(String path){
        preview.add(new Image(path));
        plantes.add(path);
    }

    private void listPlante(){
        GridPane gb = new GridPane();
        gb.setHgap(50);
        gb.setVgap(50);

        for(int i = 0; i<7;i++){
            int index = i;
            String text = "No." + (i+1) +" " + plantes.get(i);
            VBox box = new VBox();
            Button b = new Button(text);
            b.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> plante.setImage(preview.get(index)));
            b.setSkin(new MyButtonSkin(b));
            b.setPrefSize((Main.WIDTH - left.getPrefWidth() - box.getPrefWidth())/3, Main.HEIGHT/10f);
            b.setText(text);
            box.getChildren().add(b);
            noms.add(box);
        }
        for(VBox vb : noms){
            plantList.getChildren().add(vb);
        }
        scroll.setContent(plantList);

        VBox ajouter = new VBox();
        Button b = new Button("Ajouter Plante");
        b.setOnMouseClicked(mouseEvent -> {
            final Stage eventPopUp = new Stage();
            eventPopUp.setTitle("Event Pop Up");
            eventPopUp.initModality(Modality.APPLICATION_MODAL);
            //eventPopUp.initOwner(stage);
            PlantePopUp popUp = new PlantePopUp(this);
            Scene popUpScene = new Scene(popUp);
            eventPopUp.setScene(popUpScene);
            eventPopUp.show();
        });
        b.setSkin(new MyButtonSkin(b));
        b.setPrefSize((Main.WIDTH - left.getPrefWidth() - ajouter.getPrefWidth())/3, Main.HEIGHT/10f);
        ajouter.getChildren().add(b);


        gb.add(scroll,0,1);
        gb.add(ajouter,0,2);

        setRight(gb);
    }

    public void addPlante(String nomPlante){
        addImagePlante("sedum.jpg");
        int index = noms.size();
        String text = "No." + (index+1) +" " + nomPlante;
        VBox box = new VBox();
        Button b = new Button(text);
        b.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> plante.setImage(preview.get(index)));
        b.setSkin(new MyButtonSkin(b));
        b.setPrefSize((Main.WIDTH - left.getPrefWidth() - box.getPrefWidth())/3, Main.HEIGHT/10f);
        b.setText(text);
        box.getChildren().add(b);
        noms.add(box);
        plantList.getChildren().clear();
        for(VBox vb : noms){
            plantList.getChildren().add(vb);
        }
    }

    private void setButtonActions(){
        left.getChildren().get(0).setOnMouseClicked(mouseEvent -> getScene().setRoot(agenda));
        left.getChildren().get(2).setOnMouseClicked(mouseEvent -> getScene().setRoot(meteo));
    }



    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

}
