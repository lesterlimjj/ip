package parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import command.AddDeadlineCommand;
import command.AddEventCommand;
import command.AddTodoCommand;
import command.ByeCommand;
import command.Command;
import command.DeleteCommand;
import command.ListCommand;
import command.MarkCommand;
import command.UnmarkCommand;
import exception.OwenException;
import task.Deadline;
import task.Event;
import task.Todo;



public class Parser {
    private static final String[] LOCAL_DATETIME_PATTERNS = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};
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
        default:
            throw new OwenException("I have not seen that command before. Maybe in another life?");
        }
    }

    public static void checkValidTodo(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("You forgot your description. Try again.");
        }
    }

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

    public static void checkValidMark(String[] parts) throws OwenException {
        if (parts.length == 1) {
            throw new OwenException("Please specify an index. Try again.");
        } else if (parts.length > 2) {
            throw new OwenException("Too many parameters for a mark. Limit it to just one index.");
        }
    }

    public static Deadline createDeadline(String[] parts) throws OwenException {
        LocalDateTime date = processLocalDateTime(parts[1].trim());
        if (date == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Deadline newDeadline = new Deadline(parts[0], date);
        return newDeadline;
    }

    public static Event createEvent(String[] parts) throws OwenException {
        LocalDateTime date1 = processLocalDateTime(parts[1].trim());
        LocalDateTime date2 = processLocalDateTime(parts[2].trim());
        if (date1 == null || date2 == null) {
            throw new OwenException("Given datetime is in wrong format. Please use M/d/yyyy HHmm or d/M/yyyy HHmm");
        }
        Event newEvent = new Event(parts[0], date1, date2);
        return newEvent;
    }

    public static void trimStringArray(String[] array) {
        // remove all lead and trailing whitespaces
        for (int j = 0; j < array.length; j++) {
            array[j] = array[j].trim();
        }
    }

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
