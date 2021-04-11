package PIIA.Plante;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;

public class FichePlante extends BorderPane {
    private Plante plante;
    private String nom;
    private Image image;
    private final HBox plantList = new HBox();
    private ArrayList<Image> photos = new ArrayList<>();
    private HBox upper = new HBox();
    private ScrollPane scroll = new ScrollPane();

    public FichePlante(String nom,String photo,Plante plante){
        this.nom = nom;
        this.plante = plante;
        this.image = new Image(photo);
        listPlante();
    }

    private void listPlante(){
        for(VBox vb : plante.getNoms()){
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
