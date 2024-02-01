package projet.bananaspoker.ihm.stage;

import javafx.stage.Stage;

public class StageTest extends Stage {
    public StageTest() // fxml -> "accueil"
    {
        this.setTitle("Bananas' Poker");
        this.setMinWidth(1200);
        this.setMinHeight(1000);
        this.setResizable(false);
    }
}
