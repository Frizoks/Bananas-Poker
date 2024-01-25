package projet.bananaspoker.ihm;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import projet.bananaspoker.ihm.stage.Gestionnaire;

import java.util.Objects;

public class PokerApplication extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		Stage stage = Gestionnaire.creer( "accueil" );
		stage.show();
	}
}
