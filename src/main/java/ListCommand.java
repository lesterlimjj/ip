public class ListCommand extends Command {
    public static final String KEY_WORD = "list";

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ui.showTaskList(taskList.getTaskList());
    }
}
