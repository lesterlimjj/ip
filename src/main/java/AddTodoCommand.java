public class AddTodoCommand extends Command {
    public static final String keyWord = "todo";
    private Todo pendingTodo;

    public AddTodoCommand(Todo todo) {
        pendingTodo = todo;
    }

    @Override
    public void execute(UI ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingTodo);
        storage.appendToTasklistData(pendingTodo, taskList.getTaskList());
        ui.showMessage("The following Todo has been added: \n" + pendingTodo.toString() + "\n");
        ui.showSeparator();
    }

}
