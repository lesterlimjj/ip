package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents an action for interacting with ui, storage and task list.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param gui The ui for text display.
     * @param storage The storage for saving and loading tasks.
     * @param taskList The task list to be modified.
     */
    public abstract void execute(GuiController gui, Storage storage, TaskList taskList);

}
