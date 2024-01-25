package projet.bananaspoker.ihm.stage;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Popup;
import javafx.stage.Stage;
import projet.bananaspoker.ihm.PopUp;

import java.net.URL;
import java.util.ResourceBundle;

public class StageAccueil extends Stage implements Initializable
{
    public StageAccueil() // fxml -> "accueil"
    {
        this.setTitle("Bananas' Poker");
        this.setMinWidth(700);
        this.setMinHeight(500);
        this.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setWidth( this.getMinWidth() );
        this.setHeight( this.getMinHeight() );
    }

    public void onBtnCreer()
    {
        Stage stage = Gestionnaire.creer("creerSalon",this);
        if ( stage != null ) stage.show();
    }

    public void onBtnRejoindre()
    {
        PopUp.information("Rejoindre un salon",null,"Tkt mon grand");
    }
}