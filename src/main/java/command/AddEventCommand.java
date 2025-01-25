package command;

import storage.Storage;
import task.Event;
import task.TaskList;
import ui.Ui;

public class AddEventCommand extends Command {
    public static final String KEY_WORD = "event";
    private Event pendingEvent;

    public AddEventCommand(Event event) {
        pendingEvent = event;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingEvent);
        storage.appendToTasklistData(pendingEvent);
        ui.showMessage("The following event has been added: \n" + pendingEvent.toString() + "\n");
        ui.showNumberOfTasks(taskList.getTaskList());
    }

}
