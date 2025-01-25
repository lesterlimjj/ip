public class AddTodoCommand extends Command {
    public static final String KEY_WORD = "todo";
    private Todo pendingTodo;

    public AddTodoCommand(Todo todo) {
        pendingTodo = todo;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingTodo);
        storage.appendToTasklistData(pendingTodo);
        ui.showMessage("The following Todo has been added: \n" + pendingTodo.toString() + "\n");
        ui.showNumberOfTasks(taskList.getTaskList());
    }

}
