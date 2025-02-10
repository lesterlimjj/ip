package owen.command;

import owen.storage.Storage;
import owen.task.Deadline;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to add a deadline to the task list.
 */
public class AddDeadlineCommand extends Command {

    /** The key word to trigger this command. */
    public static final String KEY_WORD = "deadline";

    /** The deadline to be added to the task list. */
    private Deadline pendingDeadline;

    /**
     * Creates an AddDeadlineCommand object.
     *
     * @param deadline The deadline to be added to the task list.
     * */
    public AddDeadlineCommand(Deadline deadline) {
        pendingDeadline = deadline;
        assert pendingDeadline != null : "pendingDeadline should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.addTask(pendingDeadline);
        storage.appendToTasklistData(pendingDeadline);
        guiController.addUserDialog();
        String response = "The following deadline has been added: \n" + pendingDeadline.toString() + "\n";
        response += "You now have " + taskList.getTaskList().size() + " tasks in the list.";
        guiController.addOwenDialog(response);
    }

}
