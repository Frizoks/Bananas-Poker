package projet.bananaspoker.ihm.stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import projet.bananaspoker.ihm.PokerApplication;
import projet.bananaspoker.metier.Joueur;
import projet.bananaspoker.metier.Salle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StageSalleAttente extends Stage implements Initializable {
    @FXML
    public GridPane grdTabJoueur;
    private ArrayList<Label> lstLabel;

    private static Thread serveur;
    private Salle salle;

    private int cptCol;
    private int cptLig;

    public StageSalleAttente() // fxml -> "salleAttente"
    {
        this.setTitle("Bananas' Poker");
        this.setMinWidth(600);
        this.setMinHeight(350);
        this.setResizable(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        grdTabJoueur.getChildren().clear();
        lstLabel = new ArrayList<>();
        cptCol = cptLig = 0;

        this.setOnCloseRequest(event -> {
            if ( salle.getNbJoueursTot() < this.lstLabel.size()){
                serveur.interrupt();
                salle.deconnection(lstLabel.get(0).getText());
            }
            Stage stage = Gestionnaire.creer("accueil");
            stage.show();
        });
    }

    public void setSalle(Salle salle){ this.salle = salle; }

    public void actualiser( )
    {
        grdTabJoueur.getChildren().clear();
        GridPane grdPaneTemp = new GridPane();

        Image pathImgHomme = new Image(PokerApplication.class.getResourceAsStream("images/icons/ppHomme.png"));
        Image pathImgFemme = new Image(PokerApplication.class.getResourceAsStream("images/icons/ppFemme.png"));

        for ( Joueur j : salle.getConnections() ) {
            ImageView imageView = new ImageView((int) (Math.random() * 2) == 1 ? pathImgHomme : pathImgFemme);

            Label labelNom = new Label(j.getNomJoueur());

            // Définir les propriétés du label pour correspondre au style du FXML
            labelNom.setTextFill(Color.WHITE);
            labelNom.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 24));
            labelNom.setPadding(new Insets(0, 0, 0, 20)); // Correspond aux valeurs définies dans le FXML

            imageView.setFitHeight(70);
            imageView.setFitWidth(40);

            if (cptCol > 1) {
                cptCol = 0;
                cptLig++;
            }

            grdPaneTemp.add(imageView, 0, 0);
            grdPaneTemp.add(labelNom, 1, 0);
            grdTabJoueur.add(grdPaneTemp, cptCol++, cptLig);
        }
    }
}
