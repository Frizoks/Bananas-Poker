package projet.bananaspoker.ihm;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class PopUp
{
	private static Alert alert(Alert.AlertType type, String title, String headerText, String contentText)
	{
		Alert a = new Alert(type);

		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(contentText);

		return a;
	}

	public static Alert information(String title, String headerText, String contentText)  { return alert(Alert.AlertType.INFORMATION,  title, headerText, contentText); }
	public static Alert confirmation(String title, String headerText, String contentText) { return alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText); }
	public static Alert error(String title, String headerText, String contentText)        { return alert(Alert.AlertType.ERROR,        title, headerText, contentText); }
	public static Alert warning(String title, String headerText, String contentText)      { return alert(Alert.AlertType.WARNING,      title, headerText, contentText); }

	public static boolean confirmationR(String title, String headerText, String contentText)
	{
		Optional<ButtonType> result = alert(Alert.AlertType.CONFIRMATION, title, headerText, contentText).showAndWait();

		return result.isPresent() && result.get() == ButtonType.OK;
	}

	public static TextInputDialog textInputDialog(String defaultText, String title, String headerText, String contentText)
	{
		TextInputDialog t = new TextInputDialog(defaultText);

		t.setTitle(title);
		t.setHeaderText(headerText);
		t.setContentText(contentText);

		return t;
	}
}
