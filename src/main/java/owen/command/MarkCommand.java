package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to mark a task from the task list.
 */
public class MarkCommand extends Command {

    /** Keyword to identify mark command */
    public static final String KEY_WORD = "mark";

    /** Index of the task to be marked */
    private int pendingTaskIndex;

    /**
     * Constructs a MarkCommand object.
     *
     * @param taskIndex Index of the task to be marked.
     */
    public MarkCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.markTaskAsDone(pendingTaskIndex);
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses("The following is now done: ",
                taskList.getTaskStatus(pendingTaskIndex));
        guiController.addOwenDialog(completeResponse);
    }
}
