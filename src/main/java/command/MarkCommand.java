package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

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
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsDone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now done: \n" + taskList.getTaskStatus(pendingTaskIndex));
    }
}
