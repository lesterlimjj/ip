package owen.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * class that represents main window UI Component
 */
public class OwenWindow extends AnchorPane {

    /** vertical scrollbar for dialog container */
    @FXML
    private ScrollPane scrollPane;

    /** container for dialogs */
    @FXML
    private VBox dialogContainer;

    /** text field for user input */
    @FXML
    private TextField userInput;

    /** button for sending user input */
    @FXML
    private Button sendButton;

    /**
     * binds guiController to textField and VBox
     */
    @FXML
    public void initialize() {
        GuiController guiController = GuiController.getInstance();
        guiController.setDialogContainer(dialogContainer);
        guiController.setUserTextField(userInput);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }


    /**
     * called when user clicks send button
     * tells guiController to evaluate user input
     */
    @FXML
    private void handleUserInput() {
        GuiController guiController = GuiController.getInstance();
        guiController.evaluateInput();
    }
}
