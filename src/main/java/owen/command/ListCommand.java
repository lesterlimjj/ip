package owen.command;

import java.util.ArrayList;

import owen.storage.Storage;
import owen.task.Task;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to lists all tasks from the task list.
 */
public class ListCommand extends Command {

    /** Key word for list Owen.command. */
    public static final String KEY_WORD = "list";

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        ArrayList<Task> tasks = taskList.getTaskList();
        guiController.addUserDialog();
        String formattedSearchResults = taskList.convertTaskListToFormattedString(tasks);
        String completeResponse = guiController.formatResponses("Friend, here is your list of tasks: ",
                formattedSearchResults);
        guiController.addOwenDialog(completeResponse);
    }
}
