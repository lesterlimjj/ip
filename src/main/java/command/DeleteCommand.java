package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

public class DeleteCommand extends Command{
    public static final String KEY_WORD = "delete";
    private int pendingTaskIndex;

    public DeleteCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        String taskStatus = taskList.getTaskStatus(pendingTaskIndex);
        taskList.deleteTask(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now deleted: \n" + taskStatus);
        ui.showNumberOfTasks(taskList.getTaskList());
    }
}
