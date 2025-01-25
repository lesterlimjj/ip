public class AddEventCommand extends Command {
    public static final String keyWord = "event";
    private Event pendingEvent;

    public AddEventCommand(Event event) {
        pendingEvent = event;
    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingEvent);
        storage.appendToTasklistData(pendingEvent, taskList.getTaskList());
        ui.showMessage("The following event has been added: \n" + pendingEvent.toString() + "\n");
        ui.showSeparator();
    }

}
