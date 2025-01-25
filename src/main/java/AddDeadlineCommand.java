public class AddDeadlineCommand extends Command {
    public static final String keyWord = "deadline";
    private Deadline pendingDeadline;

    public AddDeadlineCommand(Deadline deadline) {
        pendingDeadline = deadline;
    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingDeadline);
        storage.appendToTasklistData(pendingDeadline, taskList.getTaskList());
        ui.showMessage("The following event has been added: \n" + pendingDeadline.toString() + "\n");
        ui.showSeparator();
    }

}