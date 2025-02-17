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
     * Constructs a FindCommand object.
     *
     * @param searchWord Word to search for in tasks.
     */
    public FindCommand(String searchWord) {
        this.searchWord = searchWord;
        assert searchWord != null : "searchWord should not be null";
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        ArrayList<Task> searchResults = taskList.searchTasks(searchWord);
        String formattedSearchResults = taskList.convertTaskListToFormattedString(searchResults);
        String completeResponse = guiController.formatResponses("Friend, here are the results of your search: ",
                formattedSearchResults);
        guiController.addUserDialog();
        guiController.addOwenDialog(completeResponse);
    }
}
