package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;


/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {

    /** Keyword for delete command. */
    public static final String KEY_WORD = "delete";

    /** Index of the task to be deleted. */
    private int pendingTaskIndex;

    /**
     * Constructor for DeleteCommand.
     *
     * @param taskIndex Index of the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        String taskStatus = taskList.getTaskStatus(pendingTaskIndex);
        taskList.deleteTask(pendingTaskIndex);
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses("The following is now deleted: ",
                taskStatus,
                "You now have " + String.valueOf(taskList.getTaskList().size()) + " tasks in the list.");
        guiController.addOwenDialog(completeResponse);
    }
}
