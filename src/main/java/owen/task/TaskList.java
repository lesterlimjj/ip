package owen.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {

    /** ArrayList of tasks */
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     * */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to be deleted.
     * */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * Returns a copy of the task list.
     *
     * @return Copy of the task list.
     * */
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns the status of the task at the given index.
     *
     * @param index The index of the task.
     * @return The status of the task.
     * */
    public String getTaskStatus(int index) {
        return tasks.get(index).toString();
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index The index of the task.
     */
    public void markTaskAsDone(int index) {
        tasks.get(index).setAsDone();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index The index of the task.
     */
    public void markTaskAsUndone(int index) {
        tasks.get(index).setAsNotDone();
    }

    /**
     * Searches for tasks based on a word.
     *
     * @param searchWord The word to search for in task list.
     * @return A list of matched tasks.
     */
    public ArrayList<Task> searchTasks(String searchWord) {
        ArrayList<Task> foundTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(searchWord)) {
                foundTasks.add(tasks.get(i));
            }
        }
        return foundTasks;
    }

    /**
     * Formats the tasks in a task list into a string.
     * Each line contains index and task description.
     *
     * @param tasks The list of tasks to be formatted.
     * @return A string containing the formatted tasks in the list.
     */
    public String convertTaskListToFormattedString(ArrayList<Task> tasks) {
        StringBuilder formattedTaskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            formattedTaskList.append(i + 1);
            formattedTaskList.append(". ");
            formattedTaskList.append(tasks.get(i).toString());
            formattedTaskList.append("\n");
        }
        return formattedTaskList.toString();
    }

    /**
     * Adds a tag to the task at the given index.
     *
     * @param index The index of the task to add tag to.
     * @param tag The tag to be added.
     */
    public void addTagToTask(int index, String tag) {
        tasks.get(index).addTag(tag);
    }

}
