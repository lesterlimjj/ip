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
        ArrayList<String> taskList = new ArrayList<>();

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
                        if (taskMessage.equals("bye")) {
                            System.out.println(exitMessage + "\n");
                            break;
                        } else if (taskMessage.equals("list")) {
                            for (int i = 0; i < taskList.size(); i++) {
                                int index = i + 1;
                                System.out.println(index + ". " + taskList.get(i));
                            }
                        } else {
                            System.out.println("added: " + taskMessage + "\n");
                            taskList.add(taskMessage);
                        }
                    }
                case "bye":
                    break;

            }
        }
    }
}
