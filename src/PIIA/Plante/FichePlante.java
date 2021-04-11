package PIIA.Plante;

import PIIA.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FichePlante extends BorderPane {
    private Plante plante;
    private String nom;
    private Image image;
    private HBox plantList = new HBox();
    private ArrayList<Image> images = new ArrayList<>();
    private ImageView imagePlante;
    private ScrollPane scroll = new ScrollPane();
    private final FileChooser fileChooser = new FileChooser();


    /*public FichePlante(String nom,String photo,Plante plante){
        this.nom = nom;
        this.plante = plante;
        this.image = new Image(photo);
        listPlante();
        System.out.println(plante.getNoms());
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT/10f));
    }*/
    public FichePlante(String nom,String photo){
        this.nom = nom;
        this.image = new Image(photo);
        listPlante();
        previewPlante();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT/10f));
    }

    private void previewPlante(){
        GridPane gb = new GridPane();
        gb.setHgap(50);
        gb.setVgap(10);
        FlowPane layout = new FlowPane();
        imagePlante = new ImageView(this.image);
        imagePlante.setFitHeight(500);
        imagePlante.setFitWidth(400);

        layout.getChildren().add(imagePlante);

        javafx.scene.text.Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        Text titre = new Text(this.nom);
        titre.setUnderline(true);
        titre.setFont(font);

        //Bouton ajouter une photo
        VBox add = new VBox();
        Button ajouter = new Button("Ajouter photo");
        add.getChildren().add(ajouter);
        ajouter.setOnAction(actionEvent -> {
            File f = fileChooser.showOpenDialog(ajouter.getScene().getWindow());
            if (f != null) {
                addPhoto(f.getPath());
            }
        });

        gb.add(titre,1,0);
        gb.add(layout, 1,2);
        gb.add(ajouter,1,3);
        setCenter(gb);

    }

    private void addPhoto(String path){
        this.images.add(new Image(path));
    }


    private void listPlante(){
        for(VBox vb : Plante.getNoms()){
            plantList.getChildren().add(vb);
        }
        scroll.setContent(plantList);
        setTop(scroll);
    }

    public String getNom() {
        return nom;
    }

    public Image getImage() {
        return image;
    }
}
