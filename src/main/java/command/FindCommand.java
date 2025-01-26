package command;

import java.util.ArrayList;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Represents a command to find tasks that contain a certain word.
 */
public class FindCommand extends Command {

    /** Key word for find command */
    public static final String KEY_WORD = "find";

    /** Word to search for in tasks */
    private String searchWord;

    /**
     * Constructor for FindCommand.
     * @param searchWord Word to search for in tasks.
     */
    public FindCommand(String searchWord) {
        this.searchWord = searchWord;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ArrayList<Task> searchResults = taskList.searchTasks(searchWord);
        StringBuilder output = new StringBuilder("Friend, here are the results of your search: \n");
        for (int i = 0; i < searchResults.size(); i++) {
            int index = i + 1;
            Task currentTask = searchResults.get(i);
            output.append(index + "." + currentTask.toString() + "\n");
        }
        ui.showMessage(output.toString());

    }
}
