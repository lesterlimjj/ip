import java.util.ArrayList;
import java.util.Scanner;

public class Owen {
    public static void main(String[] args) {
        String greetMessage = "Greetings! I am Owen.\n" +
                "What would you ask of me? \n";
        String exitMessage = "\nI am sure we will see each other soon. Goodbye.";
        Scanner scanner = new Scanner(System.in);
        System.out.println(greetMessage);
        String currentCommand = "";
        ArrayList<Task> taskList = new ArrayList<>();

        while (!currentCommand.equals("bye")) {
            currentCommand = scanner.nextLine();
            switch (currentCommand) {
                case "echo":
                    System.out.println("In echo mode!\n");
                    while (true) {
                        String echoMessage = scanner.nextLine();
                        if (echoMessage.equals("bye")) {
                            System.out.println(exitMessage + "\n");
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
                        if (action.equals("bye")) {
                            System.out.println(exitMessage + "\n");
                            break;
                        } else if (action.equals("list")) {
                            System.out.println("Friend, here is your list of tasks:");
                            for (int i = 0; i < taskList.size(); i++) {
                                int index = i + 1;
                                Task currentTask = taskList.get(i);
                                System.out.println(index + "." + currentTask.getDoneIcon() + " "
                                        + currentTask.getDescription());
                            }
                        } else if (action.equals("mark")) {
                            int index = Integer.parseInt(taskMessage.split(" ")[1]) - 1;
                            Task currentTask = taskList.get(index);
                            currentTask.setAsDone();
                            System.out.println("The following is now done: \n"
                                    + currentTask.getDoneIcon() + " " + currentTask.getDescription());
                        } else {
                            Task newTask = new Task(taskMessage);
                            taskList.add(newTask);
                            System.out.println("added: " + newTask.getDescription() + "\n");
                        }
                    }
                    break;

                case "bye":
                    System.out.println(exitMessage + "\n");
                    break;

            }
        }
    }
}
