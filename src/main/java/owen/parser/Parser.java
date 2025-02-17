package owen.parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import owen.command.AddDeadlineCommand;
import owen.command.AddEventCommand;
import owen.command.AddTagCommand;
import owen.command.AddTodoCommand;
import owen.command.ByeCommand;
import owen.command.Command;
import owen.command.DeleteCommand;
import owen.command.FindCommand;
import owen.command.ListCommand;
import owen.command.MarkCommand;
import owen.command.UnmarkCommand;
import owen.exception.OwenException;
import owen.task.Deadline;
import owen.task.Event;
import owen.task.Todo;


/**
 * Parses the input from the user and determines the command to be executed.
 */
public class Parser {

    /** Accepted formats for localDatetime */
    private static final String[] LOCAL_DATETIME_PATTERNS = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};

    /**
     * Parses the user input based on given command.
     *
     * @param input The user input provided.
     * @return The command created from the input.
     * @throws OwenException If the input fails a check.
     */
    public static Command parse(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");
        String keyWord = inputSplitBySpace[0];

        switch (keyWord) {
        case ListCommand.KEY_WORD:
            return new ListCommand();
        case AddTodoCommand.KEY_WORD:
            return processInputforAddTodoCommand(input);
        case AddDeadlineCommand.KEY_WORD:
            return processInputforAddDeadlineCommand(input);
        case AddEventCommand.KEY_WORD:
            return processInputforAddEventCommand(input);
        case MarkCommand.KEY_WORD:
            return processInputforMarkCommand(input);
        case UnmarkCommand.KEY_WORD:
            return processInputforUnmarkCommand(input);
        case DeleteCommand.KEY_WORD:
            return processInputforDeleteCommand(input);
        case ByeCommand.KEY_WORD:
            return new ByeCommand();
        case FindCommand.KEY_WORD:
            return processInputforFindCommand(input);
        case AddTagCommand.KEY_WORD:
            return processInputforAddTagCommand(input);
        default:
            throw new OwenException("I have not seen that command before. Maybe in another life?");
        }
    }

    /**
     * Processes the input for the add tag command.
     *
     * @param input The user input provided.
     * @return The AddTagCommand created from the input.
     */
    public static AddTagCommand processInputforAddTagCommand(String input) {
        String[] inputSplitBySpace = input.split(" ", 3);
        int index = Integer.parseInt(inputSplitBySpace[1]) - 1;
        String tag = inputSplitBySpace[2];
        AddTagCommand addTagCommand = new AddTagCommand(index, tag);
        return addTagCommand;
    }

    /**
     * Processes the input for the add todo command.
     *
     * @param input The user input provided.
     * @return The AddTodoCommand created from the input.
     * @throws OwenException If the input format fails a check.
     */
    public static AddTodoCommand processInputforAddTodoCommand(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");

        checkValidTodo(inputSplitBySpace);

        String description = input.replaceFirst(inputSplitBySpace[0] + " ", "");
        Todo todo = new Todo(description);
        AddTodoCommand addTodoCommand = new AddTodoCommand(todo);
        return addTodoCommand;
    }

    /**
     * Processes the input for the add deadline command.
     *
     * @param input The user input provided.
     * @return The AddDeadlineCommand created from the input.
     * @throws OwenException If the input format fails a check.
     */
    public static AddDeadlineCommand processInputforAddDeadlineCommand(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");
        String truncatedInput = input.replaceFirst(inputSplitBySpace[0] + " ", "");
        String[] truncatedSplitBySpace = truncatedInput.split(" ");

        checkValidDeadline(truncatedSplitBySpace);

        String[] truncatedSplitByBy = truncatedInput.split("/by");
        trimStringArray(truncatedSplitByBy);

        Deadline deadline = createDeadline(truncatedSplitByBy);
        AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(deadline);
        return addDeadlineCommand;
    }

    /**
     * Processes the input for the add event command.
     *
     * @param input The user input provided.
     * @return The AddEventCommand created from the input.
     * @throws OwenException If the input format fails a check or if the date format is wrong.
     */
    public static AddEventCommand processInputforAddEventCommand(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");
        String truncatedInput = input.replaceFirst(inputSplitBySpace[0] + " ", "");
        String[] truncatedSplitBySpace = truncatedInput.split(" ");

        checkValidEvent(truncatedSplitBySpace);

        String[] truncatedSplitByFromTo = truncatedInput.split("/from | /to");
        trimStringArray(truncatedSplitByFromTo);

        Event event = createEvent(truncatedSplitByFromTo);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        return addEventCommand;
    }

    /**
     * Processes the input for the mark command.
     *
     * @param input The user input provided.
     * @return The MarkCommand created from the input.
     * @throws OwenException If the input format is wrong or if the date format is wrong.
     */
    public static MarkCommand processInputforMarkCommand(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");
        checkValidMark(inputSplitBySpace);
        int index = Integer.parseInt(inputSplitBySpace[1]) - 1;
        MarkCommand markCommand = new MarkCommand(index);
        return markCommand;
    }

    /**
     * Processes the input for the unmark command.
     *
     * @param input The user input provided.
     * @return The UnmarkCommand created from the input.
     * @throws OwenException If the input format is wrong.
     */
    public static UnmarkCommand processInputforUnmarkCommand(String input) throws OwenException {
        String[] inputSplitBySpace = input.split(" ");
        checkValidMark(inputSplitBySpace);
        int index = Integer.parseInt(inputSplitBySpace[1]) - 1;
        UnmarkCommand unmarkCommand = new UnmarkCommand(index);
        return unmarkCommand;
    }

    /**
     * Processes the input for the delete command.
     *
     * @param input The user input provided.
     * @return The DeleteCommand created from the input.
     */
    public static DeleteCommand processInputforDeleteCommand(String input) {
        String[] inputSplitBySpace = input.split(" ");
        int index = Integer.parseInt(inputSplitBySpace[1]) - 1;
        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }

    /**
     * Processes the input for the find command.
     *
     * @param input The user input provided.
     * @return The FindCommand created from the input.
     */
    public static FindCommand processInputforFindCommand(String input) {
        String[] inputSplitBySpace = input.split(" ", 2);
        FindCommand findCommand = new FindCommand(inputSplitBySpace[1]);
        return findCommand;
    }

    /**
     * Checks if todo format is valid.
     *
     * @param parts The string array of user input.
     * @throws OwenException If input is missing description.
     */
    public static void checkValidTodo(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("You forgot your description. Try again.");
        }
    }


    /**
     * Checks if deadline format is valid.
     *
     * @param parts The string array of user input.
     * @throws OwenException If input is missing start or end date or both.
     */
    public static void checkValidEvent(String[] parts) throws OwenException {
        Boolean hasFrom = false;
        Boolean hasTo = false;

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/from")) {
                hasFrom = true;
            }
            if (parts[i].equals("/to")) {
                hasTo = true;
            }
        }

        if (hasFrom == false && hasTo == false) {
            throw new OwenException("Missing start and end date. Please add a /from <date/time> and "
                    + "add a /to <date/time>");
        } else if (hasFrom == false) {
            throw new OwenException("Missing start date. Please add a /from <date/time>");
        } else if (hasTo == false) {
            throw new OwenException("Missing end date. Please add a /to <date/time>");
        }
    }


    /**
     * Checks if deadline format is valid.
     *
     * @param parts The string array of user input.
     * @throws OwenException If input is missing date.
     */
    public static void checkValidDeadline(String[] parts) throws OwenException {
        Boolean hasBy = false;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/by")) {
                hasBy = true;
                break;
            }
        }
        if (hasBy == false) {
            throw new OwenException("We cannot find a date. Please add a /by <date/time>");
        }
    }

    /**
     * Checks if mark format is valid.
     *
     * @param parts the string array of user input.
     * @throws OwenException If index is missing or parameters > 2.
     */
    public static void checkValidMark(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("Please specify an index. Try again.");
        } else if (parts.length > 2) {
            throw new OwenException("Too many parameters for a mark. Limit it to just one index.");
        }
    }

    /**
     * Processes the string array to get LocalDateTime and creates new deadline.
     *
     * @param parts The string array of user input.
     * @return The deadline to be added.
     * @throws OwenException If date is in wrong format, it will be null.
     */
    public static Deadline createDeadline(String[] parts) throws OwenException {
        LocalDateTime dateline = convertStringToLocalDateTime(parts[1].trim());
        if (dateline == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Deadline newDeadline = new Deadline(parts[0], dateline);
        return newDeadline;
    }

    /**
     * Processes the string array to get LocalDateTime and creates new event.
     *
     * @param parts The string array of user input.
     * @return The event to be added.
     * @throws OwenException If start or end date is in wrong format, they will be null.
     */
    public static Event createEvent(String[] parts) throws OwenException {
        LocalDateTime startDate = convertStringToLocalDateTime(parts[1].trim());
        LocalDateTime endDate = convertStringToLocalDateTime(parts[2].trim());
        if (startDate == null || endDate == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Event newEvent = new Event(parts[0], startDate, endDate);
        return newEvent;
    }

    /**
     * Removes all lead and trailing whitespaces from elements of string array.
     *
     * @param array The string array to be trimmed.
     */
    public static void trimStringArray(String[] array) {
        for (int j = 0; j < array.length; j++) {
            array[j] = array[j].trim();
        }
    }

    /**
     * Converts string to get LocalDateTime in specified patterns.
     *
     * @param dateString The date string to be processed.
     * @return The LocalDateTime to be used in deadline or event.
     */
    public static LocalDateTime convertStringToLocalDateTime(String dateString) {
        LocalDateTime date = null;
        for (int i = 0; i < LOCAL_DATETIME_PATTERNS.length; i++) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATETIME_PATTERNS[i]);
            try {
                date = LocalDateTime.parse(dateString, dateFormatter);
                break; // Exit the loop once the date is successfully parsed
            } catch (DateTimeParseException e) {
                // do nothing so that we can check for next pattern
            }
        }
        return date;
    }

    /**
     * Processes the LocalDateTime to get date string in specified patterns.
     *
     * @param dateTime The LocalDateTime to be processed.
     * @return The date string to be saved in file.
     */
    public static String convertLocalDateToPattern(LocalDateTime dateTime) {
        String dateString = "";
        for (int i = 0; i < LOCAL_DATETIME_PATTERNS.length; i++) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATETIME_PATTERNS[i]);
            try {
                dateString = dateTime.format(dateFormatter);
                break; // Exit the loop once the date is successfully formatted
            } catch (DateTimeParseException e) {
                // do nothing so that we can check for next pattern
            }
        }
        return dateString;
    }

}
