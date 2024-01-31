package projet.bananaspoker.ihm.stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import projet.bananaspoker.metier.Salle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StageSalleAttente extends Stage implements Initializable {
    @FXML
    public GridPane grdTabJoueur;
    private ArrayList<Label> lstLabel;
    private final int port = /*(int)(Math.random()*45000+1030)*/6000;

    private Thread serveur;

    private int cptCol;
    private int cptLig;

    public StageSalleAttente(int nbJoueur, String mdp, int nbJetons)
    {
        Salle salle = new Salle(this.port,nbJoueur, mdp, nbJetons);
        this.serveur = new Thread(salle);
        this.serveur.start();
        this.setOnCloseRequest(event -> {
            Stage stage = Gestionnaire.creer("accueil");
            stage.show();
            this.serveur.interrupt();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        grdTabJoueur.getChildren().clear();
        lstLabel = new ArrayList<>();
        cptCol = cptLig = 0;
    }

    public void ajouterObjet() {
        GridPane grdPaneTemp = new GridPane();

        // Ajouter des objets identiques
        ImageView imageView = new ImageView((int)(Math.random()*2) == 1 ? "@../images/icons/ppHomme.png" : "@../images/icons/ppFemme.png");
        Label labelNom = new Label("Luc");

        // Définir les propriétés du label pour correspondre au style du FXML
        labelNom.setTextFill(Color.WHITE);
        labelNom.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 24));
        labelNom.setPadding(new Insets(0, 0, 0, 20)); // Correspond aux valeurs définies dans le FXML

        imageView.setFitHeight(90);
        imageView.setFitWidth(40);

        if ( cptCol > 1 ) {
            cptCol = 0;
            cptLig++;
        }

        grdPaneTemp.add(imageView, 0, 0);
        grdPaneTemp.add(labelNom, 1, 0);
        grdTabJoueur.add(grdPaneTemp, cptCol++, cptLig);
    }
}
