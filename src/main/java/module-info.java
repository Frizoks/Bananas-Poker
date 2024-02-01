module projet.bananaspoker {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;

	opens projet.bananaspoker to javafx.fxml;
	opens projet.bananaspoker.ihm to javafx.fxml;
	opens projet.bananaspoker.ihm.stage to javafx.fxml;

	exports projet.bananaspoker;
	exports projet.bananaspoker.ihm;
	exports projet.bananaspoker.ihm.stage;
	exports projet.bananaspoker.metier;
}
