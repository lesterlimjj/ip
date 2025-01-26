package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {

    /** The key word to trigger this command. */
    public static final String KEY_WORD = "bye";

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.goodbye();
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
