import parser.Parser;
import command.Command;
import exception.OwenException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.util.Scanner;

/**
 * Represents the main class of Owen.
 * It is responsible for the overall flow of the program.
 */
public class Owen {

    /** Represents the storage of Owen. */
    private Storage storage;

    /** Represents the task list of Owen. */
    private TaskList tasks;

    /** Represents the user interface of Owen. */
    private Ui ui;

    /**
     * Constructs an Owen object.
     * loads the tasklist data from the storage into tasklist.
     */
    public Owen() {
       ui = new Ui();
       tasks = new TaskList();
       storage = new Storage();
       storage.loadTasklistData(tasks);
    }

    /**
     * Runs the program until the user exits.
     */
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

    /**
     * The main method of Owen.
     * @param args default java main method parameter.
     */
    public static void main(String[] args) {
        new Owen().run();
    }

}
