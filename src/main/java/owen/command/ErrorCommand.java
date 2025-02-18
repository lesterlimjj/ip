package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to show an error message.
 */
public class ErrorCommand extends Command {

    /** The error message to be shown to the user */
    private String errorMessage;

    /**
     * Constructs an ErrorCommand object.
     *
     * @param errorMessage Error message to be shown.
     */
    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
        assert errorMessage != null : "errorMessage should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses(errorMessage);
        guiController.addOwenDialog(completeResponse);
    }
}
