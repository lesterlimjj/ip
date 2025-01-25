public class UnmarkCommand extends Command {
    public static final String keyWord = "unmark";
    private int pendingTaskIndex;

    public UnmarkCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsUndone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now not done: \n" + taskList.getTaskStatus(pendingTaskIndex) + "\n");
        ui.showSeparator();
    }
}
