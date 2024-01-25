package projet.bananaspoker.ihm.stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SatgeCreerSalon extends Stage implements Initializable
{
    @FXML private ChoiceBox<Integer> spnNbJoueur;
    @FXML private PasswordField txtMdp;
    @FXML private TextField txtNomSalon;
    @FXML private Spinner<Integer> spnNbJeton;

    public SatgeCreerSalon() // fxml -> "creerSalon"
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

        this.spnNbJoueur.setItems(FXCollections.observableList(Arrays.asList(2,4,6,8)));
        this.spnNbJoueur.setValue(this.spnNbJoueur.getItems().get(0));

        int initialValue = 10000;
        int min = 10000;
        int max = Integer.MAX_VALUE;
        int step = 5000;

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue, step);
        this.spnNbJeton.setValueFactory(valueFactory);
    }

    public void onBtnCreer()
    {
    }

    public void onBtnAnnuler(ActionEvent actionEvent)
    {
        this.close();
    }
}
