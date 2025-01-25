public class MarkCommand extends Command {
    public static final String keyWord = "mark";
    private int pendingTaskIndex;

    public MarkCommand(int taskIndex, Task task) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsDone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now done: \n" + taskList.getTaskStatus(pendingTaskIndex) + "\n");
        ui.showSeparator();
    }
}
