package owen.ui;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import owen.Owen;

/**
 * Controller class to manage GUI updates
 */
public class GuiController {

    /** singleton instance for GUI controller */
    private static GuiController instance = null;

    /** Text field to get user input */
    private TextField userTextField;

    /** The container for the dialog */
    private VBox dialogContainer;

    /** instance of chatbot */
    private Owen owen;

    /** images for user and owen */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image owenImage = new Image(this.getClass().getResourceAsStream("/images/DaOwen.png"));

    /**
     * get singleton instance of GUI controller
     *
     * @return singleton instance of GUI controller
     */
    public static GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    /**
     * set instance of chatbot for GUI controller
     *
     * @param owen instance of chatbot
     */
    public void setOwen(Owen owen) {
        this.owen = owen;
    }

    /**
     * set user text field for GUI controller
     *
     * @param userTextField text field for user input
     */
    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    /**
     * set dialog container for GUI controller
     *
     * @param dialogContainer container for dialog
     */
    public void setDialogContainer(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * add user dialog box to dialog container
     */
    public void addUserDialog() {
        String input = userTextField.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        userTextField.clear();
    }

    /**
     * add owen dialog box to dialog container
     *
     * @param response given from processed command
     */
    public void addOwenDialog(String response) {
        dialogContainer.getChildren().addAll(
                DialogBox.getOwenDialog(response, owenImage)
        );
    }

    /**
     * append responses from processed command into one string
     * each response is separated by a newline
     *
     * @param responses given array of responses from processed command
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
     * call chatbot to evaluate input given by user
     */
    public void evaluateInput() {
        owen.evaluateInput(userTextField.getText());
    }
}
