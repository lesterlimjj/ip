package owen.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Represents the main window UI component.
 */
public class OwenWindow extends AnchorPane {

    /** Vertical scrollbar for dialog container */
    @FXML
    private ScrollPane scrollPane;

    /** Container for dialogs */
    @FXML
    private VBox dialogContainer;

    /** Text field for user input */
    @FXML
    private TextField userInput;

    /** Button for sending user input */
    @FXML
    private Button sendButton;

    /**
     * Binds guiController to textField and VBox.
     */
    @FXML
    public void initialize() {
        GuiController guiController = GuiController.getInstance();
        guiController.setDialogContainer(dialogContainer);
        guiController.setUserTextField(userInput);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        guiController.greetings();
    }


    /**
     * Calls guiController to evaluate user input.
     */
    @FXML
    private void handleUserInput() {
        GuiController guiController = GuiController.getInstance();
        guiController.evaluateInput();
    }
}
