package ui;

import java.util.List;

import task.Task;

public class Ui {
    private static final String GREET_MESSAGE = "Greetings! I am Owen.\n"
            + "What would you ask of me?";
    private static final String BYE_MESSAGE = "\nI am sure we will see each other soon. Goodbye.";
    private static final String EXIT_MESSAGE = "Exited current mode!";
    private static final String SEPARATOR = "<07=======================================================07>";

    public void welcome() {
        System.out.println(GREET_MESSAGE);
    }

    public void goodbye() {
        System.out.println(BYE_MESSAGE);
    }

    public void exitMode() {
        System.out.println(EXIT_MESSAGE);
    }

    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    public void showNumberOfTasks(List<Task> tasklist) {
        System.out.println("A quick check reviews we have " + tasklist.size() + " tasks!");
    }

    public void showTaskList(List<Task> tasklist) {
        System.out.println("Friend, here is your list of tasks:");
        for (int i = 0; i < tasklist.size(); i++) {
            int index = i + 1;
            Task currentTask = tasklist.get(i);
            System.out.println(index + "." + currentTask.toString());
        }
    }

    public void showMessage(String text) {
        System.out.println(text);
    }

}
