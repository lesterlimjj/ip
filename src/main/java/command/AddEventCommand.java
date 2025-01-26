package command;

import storage.Storage;
import task.Event;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to add an event to the task list.
 */
public class AddEventCommand extends Command {

    /** The key word to trigger this command. */
    public static final String KEY_WORD = "event";

    /** The event to be added to the task list. */
    private Event pendingEvent;

    /**
     * Creates an AddEventCommand object.
     *
     * @param event The event to be added to the task list.
     */
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
