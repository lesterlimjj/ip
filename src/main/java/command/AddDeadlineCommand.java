package command;

import storage.Storage;
import task.Deadline;
import task.TaskList;
import ui.Ui;

public class AddDeadlineCommand extends Command {
    public static final String KEY_WORD = "deadline";
    private Deadline pendingDeadline;

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