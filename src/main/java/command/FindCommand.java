package command;

import java.util.ArrayList;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

public class FindCommand extends Command {
    public static final String KEY_WORD = "find";
    private String searchWord;

    public FindCommand(String searchWord) {
        this.searchWord = searchWord;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ArrayList<Task> searchResults = taskList.searchTasks(searchWord);
        String output = "Friend, here are the results of your search: \n";
        for (int i = 0; i < searchResults.size(); i++) {
            int index = i + 1;
            Task currentTask = searchResults.get(i);
            output += index + "." + currentTask.toString() + "\n";
        }
        ui.showMessage(output);

    }
}
