package ui;

import java.util.List;

import task.Task;
/**
 * Ui class that handles text display to user
 */
public class Ui {

    /** Message to greet user */
    private static final String GREET_MESSAGE = "Greetings! I am Owen.\n"
            + "What would you ask of me?";

    /** Message to say goodbye to user */
    private static final String BYE_MESSAGE = "\nI am sure we will see each other soon. Goodbye.";

    /** Message to create separator */
    private static final String SEPARATOR = "<07=======================================================07>";

    /**
     * Displays welcome message to user
     */
    public void welcome() {
        System.out.println(GREET_MESSAGE);
    }

    /**
     * Displays goodbye message to user
     */
    public void goodbye() {
        System.out.println(BYE_MESSAGE);
    }

    /**
     * Display separator to user
     */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * displays number of tasks in tasklist
     *
     * @param tasklist the user's tasklist
     */
    public void showNumberOfTasks(List<Task> tasklist) {
        System.out.println("A quick check reviews we have " + tasklist.size() + " tasks!");
    }

    /**
     * displays the contents of the tasklist
     *
     * @param tasklist the user's tasklist
     */
    public void showTaskList(List<Task> tasklist) {
        System.out.println("Friend, here is your list of tasks:");
        for (int i = 0; i < tasklist.size(); i++) {
            int index = i + 1;
            Task currentTask = tasklist.get(i);
            System.out.println(index + "." + currentTask.toString());
        }
    }

    /**
     * display a message from a command
     *
     * @param text message to be displayed from command
     */
    public void showMessage(String text) {
        System.out.println(text);
    }

}
