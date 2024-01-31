package projet.bananaspoker.ihm.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Gestionnaire
{
	public static <T> T creer( String fxml ) {
		return Gestionnaire.creer( fxml, null );
	}

	public static <T> T creer( String fxml, Stage parent ) {
		URL fxmlUrl = Gestionnaire.class.getResource(String.format("%s.fxml", fxml));
		System.out.println(fxmlUrl);
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);

		try {
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = fxmlLoader.getController();

			if (stage == null) return null;

			if (parent != null) {
				stage.initOwner(parent);
				stage.initModality(Modality.APPLICATION_MODAL);
			}

			stage.setScene(scene);

			return fxmlLoader.getController();
		} catch (IOException e) {
			return null;
		}
	}
}
