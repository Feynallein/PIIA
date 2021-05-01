package PIIA.Plante;

import PIIA.Main;
import PIIA.Overlay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


import java.beans.EventHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;

public class FichePlante extends StackPane {
    private String nom;
    private Image image;
    private HBox plantList = new HBox();
    private ArrayList<Image> images = new ArrayList<>();
    private int indexCurrent = 0; //L'index de l'image choisis comme photo principale
    private ImageView imagePlante;
    private ScrollPane scroll = new ScrollPane();


    private BorderPane fenetre = new BorderPane();
    private Overlay overlay = new Overlay(-10,0,Main.WIDTH,Main.HEIGHT);
    Text text = new Text();



    public FichePlante(String nom,String photo){
        this.nom = nom;
        this.image = new Image(photo);
        images.add(image);
        listPlante();
        previewPlante();
        infoBox();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT/10f));

        this.getChildren().add(fenetre);

    }

    private void infoBox(){
        GridPane gb = new GridPane();
        Rectangle rect = new Rectangle(800,500);
        Rectangle rect2 = new Rectangle(rect.getX(),rect.getY(),800,rect.getHeight()/10);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        rect2.setFill(Color.TRANSPARENT);
        rect2.setStroke(Color.BLACK);
        gb.add(rect2,0,0);
        gb.add(rect,0,1);
        fenetre.setRight(gb);
    }

    private void previewPhotos(){
        this.getChildren().add(overlay);

        BorderPane bp = new BorderPane();
        GridPane gb = new GridPane();
        gb.setHgap(100);
        gb.setPadding(new Insets(10, 50, 10, 10)); //margins around the whole grid

        gb.setHgap(50);
        gb.setVgap(50);
        FlowPane layout = new FlowPane();
        imagePlante = new ImageView((images.get(indexCurrent)));
        imagePlante.setFitHeight(500);
        imagePlante.setPreserveRatio(true);
        layout.getChildren().add(imagePlante);

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12);
        text.setText((indexCurrent+1) + " / " + (images.size()));

        text.setFont(font);
        text.setFill(Color.WHITE);

        //Button flecheG = new Button("<");
        ImageView flecheG = new ImageView("flecheG.png");
        flecheG.setFitHeight(80);
        flecheG.setFitWidth(50);
        flecheG.setOnMouseClicked(mouseEvent -> {
            if( indexCurrent == 0) {
                indexCurrent = images.size() - 1;
                imagePlante.setImage(images.get(indexCurrent));

            }
            else{
                indexCurrent -= 1;
                imagePlante.setImage(images.get(indexCurrent));
            }
            text.setText((indexCurrent+1) + " / " + (images.size()));

        });

        BorderPane droite = new BorderPane();
        ImageView flecheD = new ImageView("flecheD.png");
        flecheD.setFitHeight(80);
        flecheD.setFitWidth(50);
        flecheD.setOnMouseClicked(mouseEvent -> {
            if( indexCurrent == images.size() -1) {
                indexCurrent = 0;
                imagePlante.setImage(images.get(indexCurrent));
            }
            else{
                indexCurrent += 1;
                imagePlante.setImage(images.get(indexCurrent));
            }
            text.setText((indexCurrent+1) + " / " + (images.size()));

        });


        ImageView close = new ImageView("close.png");
        close.setOnMouseClicked(mouseEvent -> {
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
        });
        droite.setTop(close);
        droite.setRight(flecheD);
        droite.setAlignment(flecheD, Pos.CENTER);




        gb.add(layout, 8,3);
        gb.add(text,8,4);

        bp.setCenter(gb);
        bp.setAlignment(gb, Pos.CENTER);

        bp.setLeft(flecheG);
        bp.setAlignment(flecheG, Pos.CENTER);

        bp.setRight(droite);
        bp.setAlignment(flecheD, Pos.CENTER);


        this.getChildren().add(bp);
    }

    private void previewPlante(){
        GridPane gb = new GridPane();
        gb.setHgap(50);
        gb.setVgap(10);
        FlowPane layout = new FlowPane();
        imagePlante = new ImageView(this.image);
        imagePlante.setFitHeight(500);
        imagePlante.setFitWidth(400);
        imagePlante.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->  previewPhotos());
      //à modifier après pour afficher l'image en overlay

        layout.getChildren().add(imagePlante);

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        Text titre = new Text(this.nom);
        titre.setUnderline(true);
        titre.setFont(font);

        //Bouton ajouter une photo
        VBox add = new VBox();
        Button ajouter = new Button("Ajouter photo");
        add.getChildren().add(ajouter);
        ajouter.setOnAction(actionEvent -> {
            //On ne peut selectionner que des images
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter( "Image", "*.jpg", "*.png", "*.jpeg");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(filter);

            File f = fc.showOpenDialog(ajouter.getScene().getWindow());


            //Ajout dans les images de la plante associée
            if (f != null) {
                String imageUrl = "file:///".concat(f.getPath());
                addPhoto(imageUrl);
            }
        });

        gb.add(titre,1,0);
        gb.add(layout, 1,2);
        gb.add(ajouter,1,3);
        fenetre.setCenter(gb);

    }

    private void addPhoto(String path){
        this.images.add(new Image(path));
    }


    private void listPlante(){
        for(VBox vb : Plante.getNoms()){

            plantList.getChildren().add(vb);
        }
        scroll.setContent(plantList);
        fenetre.setTop(scroll);
    }

    public String getNom() {
        return nom;
    }

    public Image getImage() {
        return image;
    }
}
