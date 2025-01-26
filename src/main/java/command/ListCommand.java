package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to lists all tasks from the task list.
 */
public class ListCommand extends Command {

    /** Key word for list command. */
    public static final String KEY_WORD = "list";

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ui.showTaskList(taskList.getTaskList());
    }
}
