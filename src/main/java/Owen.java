import java.util.ArrayList;
import java.util.Scanner;

public class Owen {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static final String greetMessage = "Greetings! I am Owen.\n" +
            "What would you ask of me? \n";
    private static final String byeMessage = "\nI am sure we will see each other soon. Goodbye.";
    private static final String exitMessage = "Exited current mode!";

    public static void welcome(){
        System.out.println(greetMessage);
    }

    public static void goodbye(){
        System.out.println(byeMessage);
    }
    public static void exitMode(){
        System.out.println(exitMessage + "\n");
    }

    public static void showNumberOfTasks(){
        System.out.println("A quick check reviews we have " + taskList.size() + " tasks!");
    }

    public static void showTaskList(){
        System.out.println("Friend, here is your list of tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            int index = i + 1;
            Task currentTask = taskList.get(i);
            System.out.println(index + "." + currentTask.toString());
        }
    }

    public static void processMark(int index){
        Task currentTask = taskList.get(index);
        currentTask.setAsDone();
        System.out.println("The following is now done: \n"
                + currentTask.toString());
    }

    public static void processUnmark(int index){
        Task currentTask = taskList.get(index);
        currentTask.setAsNotDone();
        System.out.println("The following is now no longer done: \n"
                + currentTask.toString());
    }

    public static void createTodo(String description){
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        System.out.println("The following Todo has been added: \n" + newTodo.toString() + "\n");
    }

    public static void createDeadline(String [] parts){
        Deadline newDeadline = new Deadline(parts[0], parts[1]);
        taskList.add(newDeadline);
        System.out.println("The following deadline has been added: \n" + newDeadline.toString() + "\n");
    }

    public static void createEvent(String [] parts){
        Event newEvent = new Event(parts[0], parts[1], parts[2]);
        taskList.add(newEvent);
        System.out.println("The following deadline has been added: \n" + newEvent.toString() + "\n");
    }

    public static void createTask(String description){
        Task newTask = new Task(description);
        taskList.add(newTask);
        System.out.println("added: " + newTask.getDescription() + "\n");
    }

    public static void main(String[] args) {
        welcome();
        String currentCommand = "";

        while (!currentCommand.equals("bye")) {
            currentCommand = scanner.nextLine();
            switch (currentCommand) {
                case "echo":
                    System.out.println("In echo mode!\n");
                    while (true) {
                        String echoMessage = scanner.nextLine();
                        if (echoMessage.equals("exit")) {
                            exitMode();
                            break;
                        } else {
                            System.out.println(echoMessage + "\n");
                        }
                    }

                case "tasklist":
                    System.out.println("In tasklist mode!\n");
                    while (true) {
                        String taskMessage = scanner.nextLine();
                        String action = taskMessage.split(" ")[0];

                        if (action.equals("exit")) {
                            exitMode();
                            break;
                        } else if (action.equals("list")) {
                            showTaskList();
                            break;
                        } else if (action.equals("mark")) {
                            int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                            processMark(index);
                        } else if (action.equals("unmark")) {
                            int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                            processUnmark(index);
                        } else if (action.equals("todo")) {
                            String truncated = taskMessage.replaceFirst(action + " ","");
                            createTodo(truncated);
                            showNumberOfTasks();
                        } else if (action.equals("deadline")) {
                            String truncated = taskMessage.replaceFirst(action + " ","");
                            String [] parts = truncated.split("/");
                            createDeadline(parts);
                            showNumberOfTasks();
                        } else if (action.equals("event")) {
                            String truncated = taskMessage.replaceFirst(action + " ","");
                            String [] parts = truncated.split("/");
                            createEvent(parts);
                            showNumberOfTasks();
                        } else {
                            createTask(taskMessage);
                            showNumberOfTasks();
                        }
                    }
                    break;

                case "bye":
                    goodbye();
                    break;

            }
        }
    }
}
