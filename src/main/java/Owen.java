import parser.Parser;
import command.Command;
import exception.OwenException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.Scanner;

public class Owen {
//    private static Scanner scanner = new Scanner(System.in);
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Owen() {
       ui = new Ui();
       tasks = new TaskList();
       storage = new Storage();
       storage.loadTasklistData(tasks);
    }

    public void run() {
        ui.welcome();
        ui.showSeparator();
        Scanner scanner = new Scanner(System.in);
        boolean isExiting = false;
        while (!isExiting) {
            try {
                String input = scanner.nextLine();
                ui.showSeparator();
                Command command = Parser.parse(input);
                command.execute(ui, storage, tasks);
                isExiting = command.isBye();

            } catch (OwenException exception) {
                ui.showMessage(exception.getMessage());
            }  catch (NumberFormatException exception) {
                ui.showMessage("please use a number for the index when performing mark, unmark or delete.");
            } catch (IndexOutOfBoundsException exception) {
                ui.showMessage("The given index does not exist in the task list. Use list command to review the valid indexes.");
            } finally {
                ui.showSeparator();
            }
        }
    }

    public static void main(String[] args) {
        new Owen().run();
    }

}
