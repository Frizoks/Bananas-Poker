package projet.bananaspoker.ihm.stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import projet.bananaspoker.metier.Salle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StageCreerSalon extends Stage implements Initializable
{
    @FXML private Spinner<Integer> spnNbJoueur;
    @FXML private PasswordField txtMdp;
    @FXML private TextField txtNomSalon;
    @FXML private Spinner<Integer> spnNbJeton;

    public StageCreerSalon() // fxml -> "creerSalon"
    {
        this.setTitle("Bananas' Poker");
        this.setMinWidth(700);
        this.setMinHeight(420);
        this.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setWidth( this.getMinWidth() );
        this.setHeight( this.getMinHeight() );

        SpinnerValueFactory<Integer> valueFactoryNbJoueur = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, 4, 1);
        this.spnNbJoueur.setValueFactory(valueFactoryNbJoueur);
        //ici
        SpinnerValueFactory<Integer> valueFactoryNbJetons = new SpinnerValueFactory.IntegerSpinnerValueFactory(5000,500000, 10000, 5000);
        this.spnNbJeton.setValueFactory(valueFactoryNbJetons);
    }

    public void onBtnCreer() {
        StageConnection stage = Gestionnaire.creer("connection");
        stage.setValues(spnNbJoueur.getValue(), txtMdp.getText().isEmpty() ? null : txtMdp.getText(), this.spnNbJeton.getValue());
        stage.show();
        this.close();
    }

    public void onBtnAnnuler()
    {
        this.close();
        Stage stage = Gestionnaire.creer("accueil");
        stage.show();
    }
}
