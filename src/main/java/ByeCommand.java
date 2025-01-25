public class ByeCommand extends Command {
    public static final String KEY_WORD = "bye";

    public ByeCommand() {

    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("\nI am sure we will see each other soon. Goodbye.");
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
