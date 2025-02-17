package owen;

import javafx.application.Application;
import owen.command.Command;
import owen.command.ErrorCommand;
import owen.exception.OwenException;
import owen.parser.Parser;
import owen.storage.Storage;
import owen.task.TaskList;
import owen.ui.GuiController;
import owen.ui.OwenApplication;


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
    private GuiController ui;

    /**
     * Constructs an Owen object.
     * Loads the tasklist data from the storage into tasklist.
     */
    public Owen() {
        ui = GuiController.getInstance();
        ui.setOwen(this);
        tasks = new TaskList();
        storage = new Storage();
        storage.loadTaskListData(tasks);
    }

    /**
     * Runs the program until the user exits.
     */
    public void run(String[] args) {
        Application.launch(OwenApplication.class, args);
    }

    /**
     * Represents the entry point of the program.
     *
     * @param args Default java main method parameter.
     */
    public static void main(String[] args) {
        new Owen().run(args);
    }

    /**
     * Evaluates the input and executes the corresponding command.
     *
     * @param input The text in the textfield.
     */
    public void evaluateInput(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(ui, storage, tasks);

        } catch (OwenException exception) {
            ErrorCommand errorCommand = new ErrorCommand(exception.getMessage());
            errorCommand.execute(ui, storage, tasks);
        } catch (NumberFormatException exception) {
            ErrorCommand errorCommand = new ErrorCommand("please use a number for the index when "
                    + "performing mark, unmark, tagging or delete.");
            errorCommand.execute(ui, storage, tasks);
        } catch (IndexOutOfBoundsException exception) {
            ErrorCommand errorCommand = new ErrorCommand("The given index does not exist in the task list. "
                    + "Use list command to review the valid indexes.");
            errorCommand.execute(ui, storage, tasks);
        }
    }
}
