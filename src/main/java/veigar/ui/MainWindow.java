package veigar.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import veigar.Veigar;
import veigar.command.CommandResult;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Veigar veigar;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image veigarImage = new Image(this.getClass().getResourceAsStream("/images/Veigar.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the veigar.Veigar instance */
    public void setVeigar(Veigar v) {
        veigar = v;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing veigar.
     * Veigar's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResult cr = veigar.getResponse(input);
        String response = cr.getFeedbackToUser();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, veigarImage)
        );
        userInput.clear();
        if (cr.isExit()) {
            Platform.exit();
        }
    }
}
