package command;

import storage.Storage;
import task.TaskList;
import task.Todo;
import ui.Ui;

/**
 * Represents a command to add a todo to the task list.
 */
public class AddTodoCommand extends Command {

    /** The key word to trigger this command. */
    public static final String KEY_WORD = "todo";

    /** The todo to be added to the task list. */
    private Todo pendingTodo;

    /**
     * Creates an AddTodoCommand object.
     *
     * @param todo The todo to be added to the task list.
     */
    public AddTodoCommand(Todo todo) {
        pendingTodo = todo;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.addTask(pendingTodo);
        storage.appendToTasklistData(pendingTodo);
        ui.showMessage("The following task.Todo has been added: \n" + pendingTodo.toString() + "\n");
        ui.showNumberOfTasks(taskList.getTaskList());
    }

}
