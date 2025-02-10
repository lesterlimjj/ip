package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {

    /** The key word to trigger this command. */
    public static final String KEY_WORD = "bye";

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String response = "I am sure we will see each other soon. Goodbye.";
        guiController.addOwenDialog(response);
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
