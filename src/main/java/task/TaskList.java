package task;

import java.util.ArrayList;

public class TaskList {
    private static ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks);
    }

    public String getTaskStatus(int index) {
        return tasks.get(index).toString();
    }

    public void markTaskAsDone(int index) {
        tasks.get(index).setAsDone();
    }

    public void markTaskAsUndone(int index) {
        tasks.get(index).setAsNotDone();
    }

}
