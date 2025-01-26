package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents an action for interacting with ui, storage and task list.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param ui the ui for text display
     * @param storage the storage for saving and loading tasks
     * @param taskList the task list to be modified
     */
    public abstract void execute(Ui ui, Storage storage, TaskList taskList);

    /**
     * Returns whether the command is an exit command.
     *
     * @return a boolean indicating if the command is an exit command
     */
    public boolean isBye() {
        return false;
    }
}
