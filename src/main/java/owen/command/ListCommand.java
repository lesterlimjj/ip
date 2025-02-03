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
        ArrayList<Task> tasklist = taskList.getTaskList();
        StringBuilder output = new StringBuilder("Friend, here is your list of tasks:\n");
        for (int i = 0; i < tasklist.size(); i++) {
            int index = i + 1;
            Task currentTask = tasklist.get(i);
            output.append("\n" + index + "." + currentTask.toString());
        }
        guiController.addUserDialog();
        String response = output.toString();
        guiController.addOwenDialog(response);
    }
}
