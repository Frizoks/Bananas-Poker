module projet.bananaspoker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens projet.bananaspoker to javafx.fxml;
    exports projet.bananaspoker;
}