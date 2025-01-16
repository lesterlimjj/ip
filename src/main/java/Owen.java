import java.util.Scanner;

public class Owen {
    public static void main(String[] args) {
        String greetMessage = "Greetings! I am Owen.\n" +
                "What would you ask of me?\n";
        String exitMessage = "\nI am sure we will see each other soon. Goodbye.";
        Scanner scanner = new Scanner(System.in);
        System.out.println(greetMessage);
        while (true) {
            String echoMessage = scanner.nextLine();
            if (echoMessage.equals("bye")) {
                System.out.println(exitMessage + "\n");
                break;
            } else {
                System.out.println(echoMessage + "\n");
            }
        }
    }
}
