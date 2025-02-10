package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.task.Todo;
import owen.ui.GuiController;

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
        assert pendingTodo != null :  "pendingTodo should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.addTask(pendingTodo);
        storage.appendToTasklistData(pendingTodo);
        guiController.addUserDialog();
        String response = "The following Todo has been added: \n" + pendingTodo.toString() + "\n";
        response += "You now have " + taskList.getTaskList().size() + " tasks in the list.";
        guiController.addOwenDialog(response);
    }

}
