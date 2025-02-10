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
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.addTask(pendingDeadline);
        storage.appendToTasklistData(pendingDeadline);
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses("The following deadline has been added:",
                pendingDeadline.toString(),
                "You now have " + String.valueOf(taskList.getTaskList().size()) + " tasks in the list.");
        guiController.addOwenDialog(completeResponse);
    }

}
