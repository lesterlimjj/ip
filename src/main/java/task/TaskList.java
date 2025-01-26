package task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {

    /** ArrayList of tasks. */
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * adds a task to the task list.
     *
     * @param task the task to be added.
     * */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * deletes a task from the task list.
     *
     * @param index the index of the task to be deleted.
     * */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * returns a copy of the task list.
     *
     * @return copy of the task list.
     * */
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks);
    }

    /**
     * returns the status of the task at the given index.
     *
     * @param index the index of the task.
     * @return the status of the task.
     * */
    public String getTaskStatus(int index) {
        return tasks.get(index).toString();
    }

    /**
     * marks the task at the given index as done.
     *
     * @param index the index of the task.
     */
    public void markTaskAsDone(int index) {
        tasks.get(index).setAsDone();
    }

    /**
     * marks the task at the given index as not done.
     *
     * @param index the index of the task.
     */
    public void markTaskAsUndone(int index) {
        tasks.get(index).setAsNotDone();
    }

}
