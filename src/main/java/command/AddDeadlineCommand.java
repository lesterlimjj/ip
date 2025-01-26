package command;

import storage.Storage;
import task.Deadline;
import task.TaskList;
import ui.Ui;

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
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingDeadline);
        storage.appendToTasklistData(pendingDeadline);
        ui.showMessage("The following deadline has been added: \n" + pendingDeadline.toString() + "\n");
        ui.showNumberOfTasks(taskList.getTaskList());
    }

}