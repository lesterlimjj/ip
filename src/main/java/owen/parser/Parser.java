package owen.parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import owen.command.AddDeadlineCommand;
import owen.command.AddEventCommand;
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

    /** accepted formats for localDatetime */
    private static final String[] LOCAL_DATETIME_PATTERNS = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};

    /**
     * Parses the user input and call checks for validity
     *
     * @param input the user input provided
     * @return the command created from the input
     * @throws OwenException if the input fails a check
     */
    public static Command parse(String input) throws OwenException {
        String[] parts = input.split(" ");
        String keyWord = parts[0];
        String truncated = "";
        int index = 0;

        switch (keyWord) {
        case ListCommand.KEY_WORD:
            ListCommand listCommand = new ListCommand();
            return listCommand;
        case AddTodoCommand.KEY_WORD:
            checkValidTodo(parts);
            String description = input.replaceFirst(keyWord + " ", "");
            Todo todo = new Todo(description);
            AddTodoCommand addTodoCommand = new AddTodoCommand(todo);
            return addTodoCommand;
        case AddDeadlineCommand.KEY_WORD:
            truncated = input.replaceFirst(keyWord + " ", "");
            parts = truncated.split(" ");
            checkValidDeadline(parts);
            parts = truncated.split("/by");
            trimStringArray(parts);
            Deadline deadline = createDeadline(parts);
            AddDeadlineCommand addDeadlineCommand = new AddDeadlineCommand(deadline);
            return addDeadlineCommand;
        case AddEventCommand.KEY_WORD:
            truncated = input.replaceFirst(keyWord + " ", "");
            parts = truncated.split(" ");
            checkValidEvent(parts);
            parts = truncated.split("/from | /to");
            trimStringArray(parts);
            Event event = createEvent(parts);
            AddEventCommand addEventCommand = new AddEventCommand(event);
            return addEventCommand;
        case MarkCommand.KEY_WORD:
            checkValidMark(parts);
            MarkCommand markCommand = new MarkCommand(Integer.parseInt(parts[1]) - 1);
            return markCommand;
        case UnmarkCommand.KEY_WORD:
            checkValidMark(parts);
            UnmarkCommand unmarkCommand = new UnmarkCommand(Integer.parseInt(parts[1]) - 1);
            return unmarkCommand;
        case DeleteCommand.KEY_WORD:
            DeleteCommand deleteCommand = new DeleteCommand(Integer.parseInt(parts[1]) - 1);
            return deleteCommand;
        case ByeCommand.KEY_WORD:
            ByeCommand byeCommand = new ByeCommand();
            return byeCommand;
        case FindCommand.KEY_WORD:
            parts = input.split(" ", 2);
            FindCommand findCommand = new FindCommand(parts[1]);
            return findCommand;
        default:
            throw new OwenException("I have not seen that command before. Maybe in another life?");
        }
    }


    /**
     * checks if todo format is valid
     *
     * @param parts the string array of user input
     * @throws OwenException if input is missing description
     */
    public static void checkValidTodo(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("You forgot your description. Try again.");
        }
    }


    /**
     * checks if deadline format is valid
     *
     * @param parts the string array of user input
     * @throws OwenException if input is missing start or end date or both
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
     * checks if deadline format is valid
     *
     * @param parts the string array of user input
     * @throws OwenException if input is missing date
     */
    public static void checkValidDeadline(String[] parts) throws OwenException {
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
    }

    /**
     * checks if mark format is valid
     *
     * @param parts the string array of user input
     * @throws OwenException if index is missing or parameters > 2
     */
    public static void checkValidMark(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("Please specify an index. Try again.");
        } else if (parts.length > 2) {
            throw new OwenException("Too many parameters for a mark. Limit it to just one index.");
        }
    }

    /**
     * process string array to get LocalDateTime and creates new deadline
     *
     * @param parts the string array of user input
     * @return the deadline to be added
     * @throws OwenException if date is in wrong format, it will be null
     */
    public static Deadline createDeadline(String[] parts) throws OwenException {
        LocalDateTime date = processLocalDateTime(parts[1].trim());
        if (date == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Deadline newDeadline = new Deadline(parts[0], date);
        return newDeadline;
    }

    /**
     * process string array to get LocalDateTime and creates new event
     *
     * @param parts the string array of user input
     * @return the event to be added
     * @throws OwenException if start or end date is in wrong format, they will be null
     */
    public static Event createEvent(String[] parts) throws OwenException {
        LocalDateTime date1 = processLocalDateTime(parts[1].trim());
        LocalDateTime date2 = processLocalDateTime(parts[2].trim());
        if (date1 == null || date2 == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Event newEvent = new Event(parts[0], date1, date2);
        return newEvent;
    }

    /**
     * remove all lead and trailing whitespaces from elements of string array
     *
     * @param array the string array to be trimmed
     */
    public static void trimStringArray(String[] array) {
        for (int j = 0; j < array.length; j++) {
            array[j] = array[j].trim();
        }
    }

    /**
     * process string to get LocalDateTime in specified patterns
     *
     * @param dateString the date string to be processed
     * @return the LocalDateTime to be used in deadline or event
     */
    public static LocalDateTime processLocalDateTime(String dateString) {
        LocalDateTime date = null;
        for (int i = 0; i < LOCAL_DATETIME_PATTERNS.length; i++) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATETIME_PATTERNS[i]);
            try {
                date = LocalDateTime.parse(dateString, dateFormatter);
                break; // Exit the loop once the date is successfully parsed
            } catch (DateTimeParseException e) {
                // do nothing and check for next pattern
            }
        }
        return date;
    }

    /**
     * process LocalDateTime to get date string in specified patterns
     *
     * @param dateTime the LocalDateTime to be processed
     * @return the date string to be saved in file
     */
    public static String convertLocalDateToPattern(LocalDateTime dateTime) {
        String dateString = "";
        for (int i = 0; i < LOCAL_DATETIME_PATTERNS.length; i++) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATETIME_PATTERNS[i]);
            try {
                dateString = dateTime.format(dateFormatter);
                break; // Exit the loop once the date is successfully formatted
            } catch (DateTimeParseException e) {
                // do nothing and check for next pattern
            }
        }
        return dateString;
    }

}
