package kiki.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import kiki.Kiki;

/**
 * Controller for the main GUI window.
 */
public class MainWindow {
    private static final String EXIT_COMMAND = "bye";

    private final Kiki kiki = new Kiki();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    /**
     * Initializes automatic scrolling and the opening message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getKikiDialog("Hello! I'm Kiki\nHow can I be of service today!"));
    }

    /**
     * Handles a submitted user command.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        String response = kiki.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getKikiDialog(response)
        );
        userInput.clear();

        if (input.equalsIgnoreCase(EXIT_COMMAND)) {
            Platform.exit();
        }
    }
}
