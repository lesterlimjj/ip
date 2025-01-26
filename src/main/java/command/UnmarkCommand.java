package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to unmark a task from the task list.
 */
public class UnmarkCommand extends Command {

    /** The keyword to identify this command. */
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
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsUndone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now not done: \n" + taskList.getTaskStatus(pendingTaskIndex));
    }
}
