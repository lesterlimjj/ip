package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to unmark a task from the task list.
 */
public class UnmarkCommand extends Command {

    /** The keyword to identify this Owen.command. */
    public static final String KEY_WORD = "unmark";

    /** The index of the task to be unmarked. */
    private int pendingTaskIndex;

    /**
      * Creates a new UnmarkCommand object.
      *
      * @param taskIndex The index of the task to be unmarked.
      */
    public UnmarkCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
        assert pendingTaskIndex >= 0 : "Task index should be non-negative";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.markTaskAsUndone(pendingTaskIndex);
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses("The following is now not done: ",
                taskList.getTaskStatus(pendingTaskIndex));
        guiController.addOwenDialog(completeResponse);
    }
}
