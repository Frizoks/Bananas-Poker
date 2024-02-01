package projet.bananaspoker.ihm.stage;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import projet.bananaspoker.metier.Salle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class StageConnection extends Stage implements Initializable
{
	private Integer port;
	public TextField txtfNSalle;
	public TextField txtfNomJ;
	private int nbJoueur;
	private String mdp;
	private int nbJetons;
	public StageConnection() // fxml -> "salleAttente"
	{
		this.port = null;
		this.setTitle("Bananas' Poker");
		this.setMinWidth(490);
		this.setMinHeight(280);
		this.setResizable(false);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// Restreindre le TextField à n'accepter que des chiffres
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("\\d*")) {
				return change;
			}
			return null;
		};

		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		txtfNSalle.setTextFormatter(textFormatter);
	}

	public void onBtnAnnuler()
	{
		this.close();
	}

	public void onBtnValider() {

		Salle salle = new Salle(Integer.parseInt(this.txtfNSalle.getText()),this.nbJoueur,this.mdp,this.nbJetons);
		this.close();

		if (port!=null)
			salle.getServeur().start();
		salle.connection(Integer.parseInt(this.txtfNSalle.getText()),this.txtfNomJ.getText());
	}

	public void setValues(Integer value, String s, Integer value1)
	{
		this.nbJoueur = value;
		this.mdp = s;
		this.nbJetons = value1;
		this.port = (int)(Math.random()*45000+1030);

		this.txtfNSalle.setText("" + port);
		this.txtfNSalle.setDisable(true);
	}
}
