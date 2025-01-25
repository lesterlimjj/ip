public class MarkCommand extends Command {
    public static final String KEY_WORD = "mark";
    private int pendingTaskIndex;

    public MarkCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsDone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now done: \n" + taskList.getTaskStatus(pendingTaskIndex));
    }
}
