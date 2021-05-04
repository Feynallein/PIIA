package PIIA.Plante;

import PIIA.Main;
import PIIA.PopUp.EventPopUp;
import PIIA.PopUp.MesurePopUp;
import PIIA.PopUp.PopUp;
import PIIA.PopUp.PromptPopUp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class FichePlante extends StackPane {
    private final String nom;
    private Image image;
    private final HBox plantList = new HBox(); //liste des plantes au dessus de la fenetre
    private final ArrayList<Image> images = new ArrayList<>(); //la liste des images de la plante
    private int indexCurrent = 0; //L'index de l'image choisis comme photo principale
    private ImageView imagePlante;
    private final ScrollPane scroll = new ScrollPane();

    /**
     * dimmension de l'info box
     */
    private final int infoWidth = 800;
    private final int infoHeight = 500;

    private final BorderPane fenetre = new BorderPane();//borderpane pour agencer les différents élements qui composent la fiche
    private Overlay overlay;
    private final Text text = new Text();
    private final TextArea commentaires = new TextArea();        //Zone de texte pour écrire des observations
    private final Plante plante;

    public FichePlante(String nom, String photo, Plante plante) {
        this.plante = plante;
        this.nom = nom;
        this.image = new Image(photo);
        images.add(image);
        listPlante();
        previewPlante();
        infoBox();
        bottom();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT / 10f));

        fenetre.setPadding(new Insets(10, 10, 0, 10));

        this.getChildren().add(fenetre);
    }

    public FichePlante(String nom, Plante plante) {
        this.plante = plante;
        this.nom = nom;
        listPlante();
        previewPlante();
        infoBox();
        bottom();
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight((Main.HEIGHT / 10f));

        fenetre.setPadding(new Insets(10, 10, 0, 10));

        this.getChildren().add(fenetre);
    }

    /**
     * Affiche en overlay les images associées à la plante
     */
    private void previewPhotos() {
        overlay = new Overlay(-10, 0, (int) fenetre.getWidth(), (int) fenetre.getHeight());
        this.getChildren().add(overlay); //permet de créer le fond noir

        BorderPane bp = new BorderPane(); //va permettre l'agencement des différents éléments de la fenetre
        GridPane gb = new GridPane();
        gb.setHgap(50);
        gb.setVgap(50);
        if (!images.isEmpty()) {
            FlowPane layout = new FlowPane();
            imagePlante = new ImageView((images.get(indexCurrent)));
            imagePlante.setFitHeight(500);
            imagePlante.setPreserveRatio(true);
            layout.getChildren().add(imagePlante);
            gb.add(layout, 8, 3);
        }

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12);
        text.setText((indexCurrent + 1) + " / " + (images.size()));
        text.setFont(font);
        text.setFill(Color.WHITE);

        //Bouton pour défiler les photos à gauche
        ImageView flecheG = new ImageView("flecheG.png");
        flecheG.setFitHeight(80);
        flecheG.setFitWidth(50);
        flecheG.setOnMouseClicked(mouseEvent -> {
            if (indexCurrent == 0) {
                indexCurrent = images.size() - 1;
                imagePlante.setImage(images.get(indexCurrent));

            } else {
                indexCurrent -= 1;
                imagePlante.setImage(images.get(indexCurrent));
            }
            text.setText((indexCurrent + 1) + " / " + (images.size()));

        });

        //Bouton pour défiler les photos à droite
        BorderPane droite = new BorderPane();
        ImageView flecheD = new ImageView("flecheD.png");
        flecheD.setFitHeight(80);
        flecheD.setFitWidth(50);
        flecheD.setOnMouseClicked(mouseEvent -> {
            if (indexCurrent == images.size() - 1) {
                indexCurrent = 0;
                imagePlante.setImage(images.get(indexCurrent));
            } else {
                indexCurrent += 1;
                imagePlante.setImage(images.get(indexCurrent));
            }
            text.setText((indexCurrent + 1) + " / " + (images.size()));
        });

        //Bouton pour fermer l'overlay
        ImageView close = new ImageView("close.png");
        close.setOnMouseClicked(mouseEvent -> {
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
        });
        droite.setTop(close);
        droite.setRight(flecheD);
        BorderPane.setAlignment(flecheD, Pos.CENTER);


        //Bouton definir photo comme défault
        VBox add = new VBox();
        Button b = new Button("Definir comme photo principale");
        add.getChildren().add(b);
        b.setOnMouseClicked(mouseEvent -> setDefaultImage(indexCurrent));
        gb.add(b, 9, 4);

        gb.add(text, 8, 4);

        bp.setCenter(gb);
        BorderPane.setAlignment(gb, Pos.CENTER);

        bp.setLeft(flecheG);
        BorderPane.setAlignment(flecheG, Pos.CENTER);

        bp.setRight(droite);
        BorderPane.setAlignment(flecheD, Pos.CENTER);

        this.getChildren().add(bp);
    }

    /**
     * Affiche l'image de la plante
     */
    private void previewPlante() {
        GridPane gb = new GridPane(); //va permettre l'agencement des elements dans la fenetre
        gb.setHgap(50);
        gb.setVgap(10);
        FlowPane layout = new FlowPane(); //va contenir l'image de la plante
        imagePlante = new ImageView(this.image);
        imagePlante.setFitHeight(500);
        imagePlante.setFitWidth(400);
        imagePlante.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> previewPhotos());

        //indique que l'image est cliquable
        imagePlante.setOnMouseEntered(mouseEvent -> setCursor(Cursor.HAND));
        imagePlante.setOnMouseExited(mouseEvent -> setCursor(Cursor.DEFAULT));

        layout.getChildren().add(imagePlante); //ajout de l'image dans le FlowPane

        //Police d'écriture et affichage du nom
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        Text titre = new Text(this.nom);
        titre.setUnderline(true);
        titre.setFont(font);

        //Bouton ajouter une photo
        VBox add = new VBox();
        Button ajouter = new Button("Ajouter photo");
        ajouter.setSkin(new TransparentButton(ajouter));
        ajouter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, Insets.EMPTY)));
        add.getChildren().add(ajouter);
        ajouter.setOnAction(actionEvent -> {
            //On ne peut selectionner que des images
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image (.jpg, .png, .jpeg)", "*.jpg", "*.png", "*.jpeg");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(filter);

            File f = fc.showOpenDialog(ajouter.getScene().getWindow());

            //Ajout dans les images de la plante associée
            if (f != null) {
                String imageUrl = "file:///".concat(f.getPath());
                addPhoto(imageUrl);
                new PopUp(plante.getAgenda().getStage(), new PromptPopUp("Photo ajoutée avec succès !"), "Confirmation");

            }
        });

        //Les différent élément sont agencée dans la gridpane gb
        gb.add(titre, 1, 1);
        gb.add(layout, 1, 2);
        gb.add(ajouter, 1, 3);
        fenetre.setCenter(gb);

    }

    /**
     * Ajoute une photo dans la liste des images associée à la plante
     */
    private void addPhoto(String path) {
        this.images.add(new Image(path));
        if (this.images.size() == 1) { //Si la fiche n'a qu'une seule image
            this.image = this.images.get(0); //elle est définie automatiquement comme image par défaut
            fenetre.setCenter(null);
            previewPlante();
        }
    }

    private void setDefaultImage(int i) {
        this.image = this.images.get(i);
        fenetre.setCenter(null);

        //actualisation de l'affichage
        previewPlante();
        this.getChildren().remove(this.getChildren().size() - 1);
        this.getChildren().remove(overlay);
    }


    /**
     * Liste des plantes au dessus
     */
    public void listPlante() {
        plantList.getChildren().clear();
        //Création de la liste
        for (int i = 0; i < Plante.getPlantes().size(); i++) {
            int index = i;
            String text = "No." + (i + 1) + " " + Plante.getPlantes().get(i).getNom();
            VBox box = new VBox();
            Button b = new Button(text);
            if (Plante.getPlantes().get(i) == this) {
                b.setBackground(new Background(new BackgroundFill(Color.GREEN, null, new Insets(0, 0, 0, 0))));

            } else {
                b.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
                b.setSkin(new TransparentButton(b));
            }
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> getScene().setRoot(Plante.getPlantes().get(index)));

            b.setPrefSize((Main.WIDTH - plante.getLeftMenu().getPrefWidth() - box.getPrefWidth()) / 5, Main.HEIGHT / 10f);
            b.setText(text);
            box.getChildren().add(b);
            plantList.getChildren().add(box);
        }
        scroll.setContent(plantList);
        fenetre.setTop(scroll);
    }

    /**
     * Première page d'information de la plante
     */
    private void infoBox() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(20, 0, 0, 0));
        bp.setPrefWidth(infoWidth);

        GridPane gb = new GridPane();
        gb.setPrefSize(infoWidth, infoHeight);
        gb.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 15);
        Text text = new Text("Info");
        text.setFont(font);

        //Titre de la page
        FlowPane header = new FlowPane();
        header.setAlignment(Pos.CENTER);
        header.setPrefWidth(infoWidth);
        header.getChildren().add(text);
        header.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //Dates clées
        dateClef(bp);

        //Mesures
        mesure(bp);


        BorderPane sub = new BorderPane(); //va contenir les boutons pour faire défiler les pages
        sub.setPadding(new Insets(5, 0, 0, 0));
        sub.setPrefWidth(infoWidth);
        /*sub.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/

        //Bouton page suivante
        FlowPane suivant = new FlowPane();
        suivant.setPrefWidth(infoWidth / 3);
        suivant.setAlignment(Pos.CENTER_LEFT);
        //Button next = new Button("page suivante");
        ImageView next = new ImageView("flecheD.png");
        next.setFitHeight(50);
        next.setFitWidth(30);
        next.setOnMouseClicked(mouseEvent -> observations());
        suivant.getChildren().add(next);

        //Bouton page précédente
        FlowPane precedent = new FlowPane();
        precedent.setPrefWidth(infoWidth / 3.);
        precedent.setAlignment(Pos.CENTER_RIGHT);
        //Button before = new Button("page precedente");
        ImageView before = new ImageView("flecheG.png");
        before.setFitHeight(50);
        before.setFitWidth(30);
        before.setOnMouseClicked(mouseEvent -> graphes());
        precedent.getChildren().add(before);

        //on place les boutons à droite et à gauche dans la borderpane sub
        sub.setRight(suivant);
        sub.setLeft(precedent);

        //L'image au centre de la borderpane sub indique dans quelle page on se situe
        ImageView page1 = new ImageView("p1.png");
        FlowPane centre = new FlowPane();
        centre.setAlignment(Pos.CENTER);
        centre.setPrefWidth(infoWidth / 3.);
        centre.getChildren().add(page1);

        sub.setCenter(centre);

        bp.setTop(header);
        bp.setBottom(sub);


        fenetre.setRight(bp);
    }

    private void dateClef(BorderPane bp) {
        FlowPane date = new FlowPane();
        date.setPrefWidth(infoWidth / 2.);
        date.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        date.setAlignment(Pos.CENTER_LEFT);


        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER_LEFT);
        gp.setHgap(20);
        gp.setVgap(infoHeight / 8.);
        int column = 1;
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 10);
        Text plantation = new Text("Date de plantation : ");
        plantation.setFont(font);

        Button bPlantation = new Button("Cliquer pour ajouter une date");
        bPlantation.setOnMouseClicked(mouseEvent -> ajoutDate("Arrosage de la plante " + this.nom, bPlantation));
        bPlantation.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
        bPlantation.setSkin(new TransparentButton(bPlantation));


        Text rempotage = new Text("Date de rempotage : ");
        rempotage.setFont(font);
        Button bRempotage = new Button("Cliquer pour ajouter une date");
        bRempotage.setOnMouseClicked(mouseEvent -> ajoutDate("Arrosage de la plante " + this.nom, bRempotage));
        bRempotage.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
        bRempotage.setSkin(new TransparentButton(bRempotage));


        Text arrosage = new Text("Date d'arrosage : ");
        arrosage.setFont(font);
        Button bArrosage = new Button("Cliquer pour ajouter une date");
        bArrosage.setOnMouseClicked(mouseEvent -> ajoutDate("Arrosage de la plante " + this.nom, bArrosage));
        bArrosage.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
        bArrosage.setSkin(new TransparentButton(bArrosage));


        Text entretien = new Text("Date d'entretien/ coupe : ");
        entretien.setFont(font);
        Button bEntretien = new Button("Cliquer pour ajouter une date");
        bEntretien.setOnMouseClicked(mouseEvent -> ajoutDate("Arrosage de la plante " + this.nom, bEntretien));
        bEntretien.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
        bEntretien.setSkin(new TransparentButton(bEntretien));


        Text recolte = new Text("Date de recolte : ");
        recolte.setFont(font);
        Button bRecolte = new Button("Cliquer pour ajouter une date");
        bRecolte.setOnMouseClicked(mouseEvent -> ajoutDate("Arrosage de la plante " + this.nom, bRecolte));
        bRecolte.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, new Insets(0, 0, 0, 0))));
        bRecolte.setSkin(new TransparentButton(bRecolte));

        gp.add(plantation, column, 0);
        gp.add(rempotage, column, 1);
        gp.add(arrosage, column, 2);
        gp.add(entretien, column, 3);
        gp.add(recolte, column, 4);

        gp.add(bPlantation, column + 1, 0);
        gp.add(bRempotage, column + 1, 1);
        gp.add(bArrosage, column + 1, 2);
        gp.add(bEntretien, column + 1, 3);
        gp.add(bRecolte, column + 1, 4);
        date.getChildren().add(gp);


        bp.setLeft(date);
    }

    private void mesure(BorderPane bp){
        BorderPane layout = new BorderPane();
        FlowPane sub = new FlowPane();
        sub.setPrefWidth(infoWidth / 2.);
        sub.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout.setCenter(sub);

        FlowPane bottom = new FlowPane();
        bottom.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        bottom.setAlignment(Pos.CENTER);
        Button ajouter = new Button("Ajouter une mesure");
        ajouter.setOnMouseClicked(mouseEvent -> new PopUp(plante.getAgenda().getStage(),
                new MesurePopUp(this), "Ajouter une mesure"));
        ajouter.setSkin(new TransparentButton(ajouter));
        ajouter.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, Insets.EMPTY)));
        bottom.getChildren().add(ajouter);
        layout.setBottom(bottom);

        bp.setRight(layout);

    }

    private void ajouterMesure(String nom, String value, ComboBox cb, BorderPane bp){

    }
    private void littleCalendar() {
        overlay = new Overlay(-10, 0, (int) fenetre.getWidth(), (int) fenetre.getHeight());
        this.getChildren().add(overlay); //permet de créer le fond noir

        BorderPane bp = new BorderPane(); //pour agencer
        FlowPane fp = new FlowPane();
        fp.setAlignment(Pos.CENTER);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        fp.getChildren().add(datePickerSkin.getPopupContent());
        bp.setCenter(fp);

        //Bouton pour fermer l'overlay
        BorderPane sub = new BorderPane();
        ImageView close = new ImageView("close.png");
        close.setOnMouseClicked(mouseEvent -> {
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
        });
        sub.setTop(close);
        bp.setRight(close);
        this.getChildren().add(bp);

        //on recupere l'id de la fiche
        int idx = -1;
        for (int i = 0; i < Plante.getPlantes().size(); i++) {
            if (Plante.getPlantes().get(i) == this)
                idx = i;
        }

        int finalIdx = idx;
        datePickerSkin.getPopupContent().setOnMouseClicked(mouseEvent -> {
            LocalDate date = datePicker.getValue();
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
            new PopUp(plante.getAgenda().getStage(),
                    new EventPopUp(plante.getAgenda(), date, plante.getAgenda().getFilters(), finalIdx), "Planifier un événement");
        });
    }

    private void ajoutDate(String text, Button b) {
        overlay = new Overlay(-10, 0, (int) fenetre.getWidth(), (int) fenetre.getHeight());
        this.getChildren().add(overlay); //permet de créer le fond noir

        BorderPane bp = new BorderPane(); //pour agencer
        FlowPane fp = new FlowPane();
        fp.setAlignment(Pos.CENTER);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        fp.getChildren().add(datePickerSkin.getPopupContent());
        bp.setCenter(fp);

        //Bouton pour fermer l'overlay
        BorderPane sub = new BorderPane();
        ImageView close = new ImageView("close.png");
        close.setOnMouseClicked(mouseEvent -> {
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
        });
        sub.setTop(close);
        bp.setRight(close);
        this.getChildren().add(bp);

        //on recupere l'id de la fiche
        int idx = -1;
        for (int i = 0; i < Plante.getPlantes().size(); i++) {
            if (Plante.getPlantes().get(i) == this)
                idx = i;
        }

        int finalIdx = idx;
        datePickerSkin.getPopupContent().setOnMouseClicked(mouseEvent -> {
            LocalDate date = datePicker.getValue();
            b.setText(date.toString());
            this.getChildren().remove(bp);
            this.getChildren().remove(overlay);
            new PopUp(plante.getAgenda().getStage(),
                    new EventPopUp(plante.getAgenda(), date,text, plante.getAgenda().getFilters(), finalIdx), "Planifier un événement");
        });
    }

    /**
     * Page pour les notes
     */
    private void observations() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(20, 0, 0, 0));
        bp.setPrefWidth(infoWidth);
        GridPane gb = new GridPane();
        gb.setPrefSize(infoWidth, infoHeight);
        gb.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 15);
        Text text = new Text("Observations / Notes");
        text.setFont(font);

        //Titre de la page (ici Observation)
        FlowPane header = new FlowPane();
        header.setAlignment(Pos.CENTER);
        header.setPrefWidth(infoWidth);
        header.getChildren().add(text);
        header.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        BorderPane sub = new BorderPane();     //va contenir les boutons pour faire défiler les pages
        sub.setPadding(new Insets(5, 0, 0, 0));
        sub.setPrefWidth(infoWidth);
        /*sub.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/


        //Bouton page suivante
        FlowPane suivant = new FlowPane();
        suivant.setPrefWidth(infoWidth / 3.);
        suivant.setAlignment(Pos.CENTER_LEFT);
        ImageView next = new ImageView("flecheD.png");
        next.setFitHeight(50);
        next.setFitWidth(30);
        next.setOnMouseClicked(mouseEvent -> graphes());
        suivant.getChildren().add(next);

        //Bouton page précédente
        FlowPane precedent = new FlowPane();
        precedent.setPrefWidth(infoWidth / 3.);
        precedent.setAlignment(Pos.CENTER_RIGHT);
        ImageView before = new ImageView("flecheG.png");
        before.setFitHeight(50);
        before.setFitWidth(30);
        before.setOnMouseClicked(mouseEvent -> infoBox());
        precedent.getChildren().add(before);

        //on place les boutons à droite et à gauche dans la borderpane sub
        sub.setRight(suivant);
        sub.setLeft(precedent);

        //L'image au centre de la borderpane sub indique dans quelle page on se situe
        ImageView page1 = new ImageView("p2.png");
        FlowPane centre = new FlowPane();
        centre.setAlignment(Pos.CENTER);
        centre.setPrefWidth(infoWidth / 3.);
        centre.getChildren().add(page1);
        sub.setCenter(centre);

        bp.setTop(header);
        bp.setCenter(commentaires);
        bp.setBottom(sub);

        fenetre.setRight(bp);
    }

    /**
     * Page des graphes
     */
    private void graphes() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(20, 0, 0, 0));
        bp.setPrefWidth(infoWidth);
        GridPane gb = new GridPane();
        gb.setPrefSize(infoWidth, infoHeight);
        gb.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 15);
        Text text = new Text("Graphes");
        text.setFont(font);

        //Titre de la page (ici Graphes)
        FlowPane header = new FlowPane();
        header.setAlignment(Pos.CENTER);
        header.setPrefWidth(infoWidth);
        header.getChildren().add(text);
        header.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        BorderPane sub = new BorderPane(); //va contenir les boutons pour faire défiler les pages
        sub.setPadding(new Insets(5, 0, 0, 0));
        sub.setPrefWidth(infoWidth);
        /*sub.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/

        //bouton page suivante
        FlowPane suivant = new FlowPane();
        suivant.setPrefWidth(infoWidth / 3.);
        suivant.setAlignment(Pos.CENTER_LEFT);
        //Button next = new Button("page suivante");
        ImageView next = new ImageView("flecheD.png");
        next.setFitHeight(50);
        next.setFitWidth(30);
        next.setOnMouseClicked(mouseEvent -> infoBox());
        suivant.getChildren().add(next);

        //bouton page précédente
        FlowPane precedent = new FlowPane();
        precedent.setPrefWidth(infoWidth / 3.);
        precedent.setAlignment(Pos.CENTER_RIGHT);
        //Button before = new Button("page precedente");
        ImageView before = new ImageView("flecheG.png");
        before.setFitHeight(50);
        before.setFitWidth(30);
        before.setOnMouseClicked(mouseEvent -> observations());
        precedent.getChildren().add(before);


        //L'image au centre de la borderpane sub indique dans quelle page on se situe
        ImageView page1 = new ImageView("p3.png");
        FlowPane centre = new FlowPane();
        centre.setAlignment(Pos.CENTER);
        centre.setPrefWidth(infoWidth / 3.);
        centre.getChildren().add(page1);

        sub.setCenter(centre);
        sub.setRight(suivant);
        sub.setLeft(precedent);

        bp.setTop(header);
        bp.setBottom(sub);

        fenetre.setRight(bp);
    }

    /**
     * Contient les élements en bas de la fenetre
     */
    private void bottom() {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(30, 100, 40, 50));

        //Bouton pour retourner à la liste des plantes
        ImageView back = new ImageView("back.png");
        back.setFitHeight(35);
        back.setFitWidth(50);
        back.setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
        bp.setLeft(back);

        //Bouton pour planifier un evenement associé à la plante
        ImageView plan = new ImageView("plan.png");
        plan.setFitHeight(50);
        plan.setFitWidth(200);
        plan.setOnMouseClicked(mouseEvent -> littleCalendar());
        bp.setCenter(plan);

        fenetre.setBottom(bp);
    }

    public String getNom() {
        return nom;
    }

    Image getImage() {
        return image;
    }
}
