public class UnmarkCommand extends Command {
    public static final String KEY_WORD = "unmark";
    private int pendingTaskIndex;

    public UnmarkCommand(int taskIndex) {
        pendingTaskIndex = taskIndex;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.markTaskAsUndone(pendingTaskIndex);
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("The following is now not done: \n" + taskList.getTaskStatus(pendingTaskIndex));
    }
}
