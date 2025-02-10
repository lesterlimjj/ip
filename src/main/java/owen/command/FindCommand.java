package owen.command;

import java.util.ArrayList;

import owen.storage.Storage;
import owen.task.Task;
import owen.task.TaskList;
import owen.ui.GuiController;

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
        assert searchWord != null : "searchWord should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        ArrayList<Task> searchResults = taskList.searchTasks(searchWord);
        StringBuilder output = new StringBuilder("Friend, here are the results of your search: \n");
        for (int i = 0; i < searchResults.size(); i++) {
            int index = i + 1;
            Task currentTask = searchResults.get(i);
            output.append(index + "." + currentTask.toString() + "\n");
        }
        guiController.addUserDialog();
        String response = output.toString();
        guiController.addOwenDialog(response);

    }
}
