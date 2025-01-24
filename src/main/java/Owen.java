import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.*;

public class Owen {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static final String greetMessage = "Greetings! I am Owen.\n" +
            "What would you ask of me?";
    private static final String byeMessage = "\nI am sure we will see each other soon. Goodbye.";
    private static final String exitMessage = "Exited current mode!";

    public static void welcome() {
        System.out.println(greetMessage);
    }

    public static void goodbye() {
        System.out.println(byeMessage);
    }

    public static void exitMode() {
        System.out.println(exitMessage);
    }

    public static void showSeparator() {
        System.out.println("<07=======================================================07>");
    }

    public static void showNumberOfTasks() {
        System.out.println("A quick check reviews we have " + taskList.size() + " tasks!");
    }

    public static void showTaskList() {
        System.out.println("Friend, here is your list of tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            int index = i + 1;
            Task currentTask = taskList.get(i);
            System.out.println(index + "." + currentTask.toString());
        }
    }

    public static void processMark(int index) {
        Task currentTask = taskList.get(index);
        currentTask.setAsDone();
        System.out.println("The following is now done: \n"
                + currentTask.toString());
    }

    public static void processUnmark(int index) {
        Task currentTask = taskList.get(index);
        currentTask.setAsNotDone();
        System.out.println("The following is now no longer done: \n"
                + currentTask.toString());
    }

    public static void createTodo(String description) {
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        System.out.println("The following Todo has been added: \n" + newTodo.toString() + "\n");
    }

    public static void createDeadline(String[] parts) {
        Deadline newDeadline = new Deadline(parts[0], parts[1]);
        taskList.add(newDeadline);
        System.out.println("The following deadline has been added: \n" + newDeadline.toString() + "\n");
    }

    public static void createEvent(String[] parts) {
        Event newEvent = new Event(parts[0], parts[1].trim(), parts[2]);
        taskList.add(newEvent);
        System.out.println("The following deadline has been added: \n" + newEvent.toString() + "\n");
    }

    public static void processDelete(int index) {
        Task currentTask = taskList.get(index);
        System.out.println("The following task has been deleted: \n" + currentTask.toString() + "\n");
        taskList.remove(index);
    }

    public static void loadTasklist() {
        Path tasklistPath = Paths.get("./","data", "tasklist.txt");
        try {
            if (Files.exists(tasklistPath)){
                List<String> lines = Files.readAllLines(tasklistPath);
                for (int i = 0; i < lines.size(); i++) {
                    String [] parts = lines.get(i).split("\\|");
                    // remove all lead and trailing whitespaces
                    for (int j = 0; j < parts.length; j++) {
                        parts[j] = parts[j].trim();
                    }
                    boolean isDone;
                    String description;
                    String dateTime;
                    String startDate;
                    String endDate;

                    switch (parts[0]) {
                        case "T":
                            isDone = parts[1].equals("1");
                            description = parts[2];
                            Todo loadedTodo = new Todo(description, isDone);
                            taskList.add(loadedTodo);
                            break;
                        case "D":
                            isDone = parts[1].equals("1");
                            description = parts[2];
                            dateTime = parts[3];
                            Deadline loadedDeadline = new Deadline(description, isDone, dateTime);
                            taskList.add(loadedDeadline);
                            break;
                        case "E":
                            isDone = parts[1].equals("1");
                            description = parts[2];
                            startDate = parts[3].split("-")[0];
                            endDate = parts[3].split("-")[1];
                            Event loadedEvent = new Event(description, isDone, startDate, endDate);
                            taskList.add(loadedEvent);
                            break;
                    }
                }
            } else {
                if (!Files.exists(tasklistPath.getParent())) {
                    Files.createDirectory(tasklistPath.getParent());
                }

                if (!Files.exists(tasklistPath)) {
                    Files.createFile(tasklistPath);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        welcome();
        loadTasklist();
        showSeparator();
        String currentCommand = "";

        while (!currentCommand.equals("bye")) {
            try {
                currentCommand = scanner.nextLine();
                switch (currentCommand) {
                    case "echo":
                        System.out.println("In echo mode!");
                        showSeparator();
                        while (true) {
                            String echoMessage = scanner.nextLine();
                            if (echoMessage.equals("exit")) {
                                exitMode();
                                break;
                            } else {
                                System.out.println(echoMessage);
                            }
                            showSeparator();
                        }
                        break;

                    case "tasklist":
                        System.out.println("In tasklist mode!");
                        showSeparator();
                        while (true) {
                            try {
                                String taskMessage = scanner.nextLine();
                                String action = taskMessage.split(" ")[0];
                                if (action.equals("exit")) {
                                    exitMode();
                                    break;
                                } else if (action.equals("list")) {
                                    showTaskList();
                                } else if (action.equals("mark")) {
                                    String[] parts = taskMessage.split(" ");
                                    if (parts.length == 1) {
                                        throw new OwenException("Please specify an index. Try again.");
                                    } else if (parts.length > 2) {
                                        throw new OwenException("Too many parameters for a mark. Limit it to just one index.");
                                    }
                                    int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                                    processMark(index);
                                } else if (action.equals("unmark")) {
                                    String[] parts = taskMessage.split(" ");
                                    if (parts.length == 1) {
                                        throw new OwenException("Please specify an index. Try again.");
                                    } else if (parts.length > 2) {
                                        throw new OwenException("Too many parameters for an unmark. Limit it to just one index.");
                                    }
                                    int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                                    processUnmark(index);
                                } else if (action.equals("todo")) {
                                    String[] parts = taskMessage.split(" ");
                                    if (parts.length == 1) {
                                        throw new OwenException("You forgot your description. Try again.");
                                    }
                                    String description = taskMessage.replaceFirst(action + " ", "");
                                    createTodo(description);
                                    showNumberOfTasks();
                                } else if (action.equals("deadline")) {
                                    String truncated = taskMessage.replaceFirst(action + " ", "");
                                    String[] parts = truncated.split(" ");
                                    Boolean byPresent = false;
                                    for (int i = 0; i < parts.length; i++) {
                                        if (parts[i].equals("/by")) {
                                            byPresent = true;
                                            break;
                                        }
                                    }
                                    if (byPresent == false) {
                                        throw new OwenException("We cannot find a date. Please add a /by <date/time>");
                                    }
                                    truncated = truncated.replaceFirst("by ", "");
                                    parts = truncated.split("/");
                                    createDeadline(parts);
                                    showNumberOfTasks();
                                } else if (action.equals("event")) {
                                    String truncated = taskMessage.replaceFirst(action + " ", "");
                                    String[] parts = truncated.split(" ");
                                    Boolean fromPresent = false;
                                    Boolean toPresent = false;
                                    for (int i = 0; i < parts.length; i++) {
                                        if (parts[i].equals("/from")) {
                                            fromPresent = true;
                                        }
                                        if (parts[i].equals("/to")) {
                                            toPresent = true;
                                        }
                                    }
                                    if (fromPresent == false && toPresent == false) {
                                        throw new OwenException("Missing start and end date. Please add a /from <date/time> and add a /to <date/time>");
                                    } else if (fromPresent == false) {
                                        throw new OwenException("Missing start date. Please add a /from <date/time>");
                                    } else if (toPresent == false) {
                                        throw new OwenException("Missing end date. Please add a /to <date/time>");
                                    }
                                    truncated = truncated.replaceFirst("from ", "");
                                    truncated = truncated.replaceFirst("to ", "");
                                    parts = truncated.split("/");
                                    createEvent(parts);
                                    showNumberOfTasks();
                                } else if (action.equals("delete")) {
                                    String[] parts = taskMessage.split(" ");
                                    if (parts.length == 1) {
                                        throw new OwenException("Please specify an index. Try again.");
                                    } else if (parts.length > 2) {
                                        throw new OwenException("Too many parameters for a delete. Limit it to just one index.");
                                    }
                                    int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                                    processDelete(index);
                                    showNumberOfTasks();
                                } else {
                                    throw new OwenException("I have not seen that command before. Maybe in another life?");
                                }
                                showSeparator();
                            } catch (OwenException exception) {
                                System.out.println(exception.getMessage());
                                showSeparator();
                            } catch (NumberFormatException exception) {
                                System.out.println("please use a number for the index when performing mark, unmark or delete.");
                                showSeparator();
                            } catch (IndexOutOfBoundsException exception) {
                                System.out.println("The given index does not exist in the task list. Use list command to review the valid indexes.");
                                showSeparator();
                            }
                        }
                        break;

                    case "bye":
                        goodbye();
                        break;

                    default:
                        throw new OwenException("I have not seen that command before. Maybe in another life?");
                }
                showSeparator();
            } catch (OwenException exception) {
                System.out.println(exception.getMessage());
                showSeparator();
            }
        }
    }
}
