package command;

import storage.Storage;
import task.TaskList;
import ui.Ui;

public class ByeCommand extends Command {
    public static final String KEY_WORD = "bye";

    public ByeCommand() {

    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        storage.overwriteTasklistData(taskList.getTaskList());
        ui.goodbye();
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
