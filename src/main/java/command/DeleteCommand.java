package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command{

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
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        String taskStatus = taskList.getTaskStatus(pendingTaskIndex);
        taskList.deleteTask(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now deleted: \n" + taskStatus);
        ui.showNumberOfTasks(taskList.getTaskList());
    }
}
