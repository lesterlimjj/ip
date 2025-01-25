import java.util.List;

public class UI {
    private static final String greetMessage = "Greetings! I am Owen.\n" +
            "What would you ask of me?";
    private static final String byeMessage = "\nI am sure we will see each other soon. Goodbye.";
    private static final String exitMessage = "Exited current mode!";
    private static final String separator = "<07=======================================================07>";

    public void welcome() {
        System.out.println(greetMessage);
    }

    public void goodbye() {
        System.out.println(byeMessage);
    }

    public void exitMode() {
        System.out.println(exitMessage);
    }

    public void showSeparator() {
        System.out.println(separator);
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
