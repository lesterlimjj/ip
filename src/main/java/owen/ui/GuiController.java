package owen.ui;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import owen.Owen;

/**
 * Represents a controller class to manage GUI updates
 */
public class GuiController {

    /** Singleton instance for GUI controller */
    private static GuiController instance = null;

    /** Text field to get user input */
    private TextField userTextField;

    /** The container for the dialog */
    private VBox dialogContainer;

    /** Instance of chatbot */
    private Owen owen;

    /** Images for user and owen */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image owenImage = new Image(this.getClass().getResourceAsStream("/images/DaOwen.png"));

    /**
     * Gets singleton instance of GUI controller.
     *
     * @return Singleton instance of GUI controller.
     */
    public static GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    /**
     * Sets instance of chatbot for GUI controller.
     *
     * @param owen Instance of chatbot.
     */
    public void setOwen(Owen owen) {
        this.owen = owen;
    }

    /**
     * Sets user text field for GUI controller.
     *
     * @param userTextField Text field for user input.
     */
    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    /**
     * Sets dialog container for GUI controller.
     *
     * @param dialogContainer Container for dialog.
     */
    public void setDialogContainer(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * Adds user dialog box to dialog container.
     */
    public void addUserDialog() {
        String input = userTextField.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        userTextField.clear();
    }

    /**
     * Adds owen dialog box to dialog container.
     *
     * @param response Given response from processed command.
     */
    public void addOwenDialog(String response) {
        dialogContainer.getChildren().addAll(
                DialogBox.getOwenDialog(response, owenImage)
        );
    }

    /**
     * Appends responses from processed command into one string.
     * Each response is separated by a newline.
     *
     * @param responses Given array of responses from processed command.
     * @return Complete string of responses.
     */
    public String formatResponses(String... responses) {
        StringBuilder completeResponse = new StringBuilder();
        for (String response : responses) {
            completeResponse.append(response);
            completeResponse.append("\n");
        }
        return completeResponse.toString();
    }

    /**
     * Calls chatbot to evaluate input given by user.
     */
    public void evaluateInput() {
        owen.evaluateInput(userTextField.getText());
    }
}
