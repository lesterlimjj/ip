package owen.command;

import owen.storage.Storage;
import owen.task.Event;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to add an event to the tasklist.
 */
public class AddEventCommand extends Command {

    /** The key word to trigger this Owen.command. */
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
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.addTask(pendingEvent);
        storage.appendToTasklistData(pendingEvent);
        guiController.addUserDialog();
        String response = "The following event has been added: \n" + pendingEvent.toString() + "\n";
        response += "You now have " + taskList.getTaskList().size() + " tasks in the list.";
        guiController.addOwenDialog(response);
    }

}
