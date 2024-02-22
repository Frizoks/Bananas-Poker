package projet.bananaspoker.ihm.stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import projet.bananaspoker.ihm.PokerApplication;
import projet.bananaspoker.ihm.PopUp;
import projet.bananaspoker.metier.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StageTable extends Stage implements Initializable
{

    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView img4;
    @FXML private ImageView img5;

    @FXML private ImageView main1;
    @FXML private ImageView main2;

    @FXML private ImageView jeton;
    @FXML private Label lblJetons;


    @FXML private ArrayList<ImageView> lstImg;

    public StageTable()
    {
        this.setTitle("Bananas' Poker");
        this.setMinWidth(740);
        this.setMinHeight(500);
        this.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstImg = new ArrayList<ImageView>();

        Image pathImgDos = new Image(PokerApplication.class.getResourceAsStream("images/dosCarte.png"));

        this.lstImg.add(this.img1);
        this.lstImg.add(this.img2);
        this.lstImg.add(this.img3);
        this.lstImg.add(this.img4);
        this.lstImg.add(this.img5);

        for (ImageView cartes : this.lstImg)
        {
            cartes.setImage(pathImgDos);
        }

        this.main1.setImage(pathImgDos);
        this.main2.setImage(pathImgDos);

        this.jeton.setImage(new Image(PokerApplication.class.getResourceAsStream("images/jeton.png")));
        this.lblJetons.setText("0 jeton");

        this.majCartes();

        ArrayList<Carte> cartesssssss = new ArrayList<Carte>();
        for (int i = 0; i < 5; i++) {
            cartesssssss.add(new Carte(Carte.CARREAU,5,""));
        }

        majCartes(cartesssssss);

        cartesssssss.clear();
        cartesssssss.add(new Carte(Carte.PIQUE, 8, ""));
        cartesssssss.add(new Carte(Carte.PIQUE, 12, ""));

        majMain(cartesssssss);
        majJetons(5);
    }

    public void majCartes(){
        Image pathImgDos = new Image(PokerApplication.class.getResourceAsStream("images/dosCarte.png"));
        for (ImageView img : lstImg){
            img.setImage(pathImgDos);
        }
    }

    public void majCartes (ArrayList<Carte> lstCartes){
        //si la première carte est nul, la liste sera considérer comme nul et on affichera le dos des cartes au centre de la table
        if (lstCartes.isEmpty() || lstCartes.get(0) == null) {
            this.majCartes();
            return;
        }

        //sinon on affiche les cartes qui seront prises
        int i = 0;
        for ( Carte c : lstCartes) {
            Image pathImg = new Image(PokerApplication.class.getResourceAsStream(Carte.getImageCarte(c)));
            this.lstImg.get(i).setImage(pathImg);
            i++;
        }
    }

    public void majMain (ArrayList<Carte> main){
        Image pathImg = new Image(PokerApplication.class.getResourceAsStream(Carte.getImageCarte(main.get(0))));
        main1.setImage(pathImg);
        pathImg = new Image(PokerApplication.class.getResourceAsStream(Carte.getImageCarte(main.get(1))));
        main2.setImage(pathImg);
    }


    public void majJetons (int i){
        if (i<0)
            return;

        if (i == 1 || i == 0)
            this.lblJetons.setText("" + i + " jeton");
        else
            this.lblJetons.setText("" + i + " jetons");

    }




    public static void main(String[] args) {
        Stage stage = Gestionnaire.creer( "table" );
        stage.show();
    }

}