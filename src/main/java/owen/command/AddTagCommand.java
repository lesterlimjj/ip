package owen.command;

import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;

/**
 * Represents a command to add a tag to a task in tasklist.
 */
public class AddTagCommand extends Command {
    /** The key word to trigger this command. */
    public static final String KEY_WORD = "tag";

    /** Index of the task to be tagged */
    private int pendingTaskIndex;

    /** The tag to be added to the task */
    private String tag;

    /**
     * Constructs an AddTagCommand object.
     *
     * @param taskIndex The index of the task to tag.
     * @param providedTag The tag to be added to the task.
     * */
    public AddTagCommand(int taskIndex, String providedTag) {
        pendingTaskIndex = taskIndex;
        tag = providedTag;
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList taskList) {
        taskList.addTagToTask(pendingTaskIndex, tag);
        storage.overwriteTaskListData(taskList.getTaskList());
        guiController.addUserDialog();
        String completeResponse = guiController.formatResponses("The following is now tagged: ",
                taskList.getTaskStatus(pendingTaskIndex));
        guiController.addOwenDialog(completeResponse);
    }
}
