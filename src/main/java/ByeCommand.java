public class ByeCommand extends Command {
    public static final String keyWord = "bye";
    public ByeCommand() {

    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.showMessage("\nI am sure we will see each other soon. Goodbye.");
        ui.showSeparator();
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
