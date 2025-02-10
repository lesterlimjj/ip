package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to show an error message
 */
public class ErrorCommand extends Command {

    /** The error message to be shown to the user. */
    private String errorMessage;

    /**
     * Constructor for DeleteCommand.
     *
     * @param errorMessage error message to be shown.
     */
    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
        assert errorMessage != null : "errorMessage should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        storage.overwriteTasklistData(taskList.getTaskList());
        guiController.addUserDialog();
        String response = errorMessage;
        guiController.addOwenDialog(response);
    }
}
