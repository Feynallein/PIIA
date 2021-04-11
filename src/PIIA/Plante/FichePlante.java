package PIIA.Plante;

import PIIA.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.Button;


import java.awt.*;
import java.util.ArrayList;

public class FichePlante extends BorderPane {
    private Plante plante;
    private String nom;
    private Image image;
    private HBox plantList = new HBox();
    private ArrayList<Image> photos = new ArrayList<>();
    private ScrollPane scroll = new ScrollPane();

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
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT/10f));
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
