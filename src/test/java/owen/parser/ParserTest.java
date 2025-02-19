package owen.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import owen.command.AddDeadlineCommand;
import owen.command.AddEventCommand;
import owen.command.AddTagCommand;
import owen.command.AddTodoCommand;
import owen.command.ByeCommand;
import owen.command.Command;
import owen.command.DeleteCommand;
import owen.command.ListCommand;
import owen.command.MarkCommand;
import owen.command.UnmarkCommand;
import owen.exception.OwenException;
import owen.task.Deadline;
import owen.task.Event;


public class ParserTest {

    @Test
    public void parse_listCommand_success() throws OwenException {
        Command command = Parser.parse("list");
        assertEquals(ListCommand.class, command.getClass());
    }

    @Test
    public void parse_addTodoCommand_success() throws OwenException {
        Command command = Parser.parse("todo eat apple");
        assertEquals(AddTodoCommand.class, command.getClass());
    }

    @Test
    public void parse_addDeadlineCommand_success() throws OwenException {
        Command command = Parser.parse("deadline dream /by 10/3/2020 2000");
        assertEquals(AddDeadlineCommand.class, command.getClass());
    }

    @Test
    public void parse_addEventCommand_success() throws OwenException {
        Command command = Parser.parse("event eat death /from 2/12/2019 1800 /to 2/12/2020 2000");
        assertEquals(AddEventCommand.class, command.getClass());
    }

    @Test
    public void parse_markCommand_success() throws OwenException {
        Command command = Parser.parse("mark 1");
        assertEquals(MarkCommand.class, command.getClass());
    }

    @Test
    public void parse_unmarkCommand_success() throws OwenException {
        Command command = Parser.parse("unmark 1");
        assertEquals(UnmarkCommand.class, command.getClass());
    }

    @Test
    public void parse_deleteCommand_success() throws OwenException {
        Command command = Parser.parse("delete 1");
        assertEquals(DeleteCommand.class, command.getClass());
    }

    @Test
    public void parse_byeCommand_success() throws OwenException {
        Command command = Parser.parse("bye");
        assertEquals(ByeCommand.class, command.getClass());
    }

    @Test
    public void parse_addTagCommand_success() throws OwenException {
        Command command = Parser.parse("tag 1 fun");
        assertEquals(AddTagCommand.class, command.getClass());
    }

    @Test
    public void parse_invalidCommand_throwsException() {
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.parse("gibber");
        });

        assertEquals("I have not seen that command before. Maybe in another life?", exception.getMessage());
    }

    @Test
    public void checkValidTodo_validTodo_success() throws OwenException {
        String[] parts = "todo eat".split(" ");
        assertDoesNotThrow(() -> {
            Parser.checkValidTodo(parts);
        });
    }

    @Test
    public void checkValidTodo_invalidTodo_throwsException() throws OwenException {
        String[] parts = "todo".split(" ");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidTodo(parts);
        });

        assertEquals("You forgot your description. Try again.", exception.getMessage());
    }

    @Test
    public void checkValidDeadline_validDeadline_success() throws OwenException {
        String input = "deadline dream /by 10/3/2020 2000";
        String truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] partsSplitBySpace = truncated.split(" ");
        String[] partsSplitByBy = truncated.split("/by");
        assertDoesNotThrow(() -> {
            Parser.checkValidDeadline(partsSplitBySpace, partsSplitByBy);
        });
    }

    @Test
    public void checkValidDeadline_invalidDeadline_throwsException() throws OwenException {
        String input = "deadline dream 10/3/2020 2000";
        String truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] partsSplitBySpace = truncated.split(" ");
        String[] partsSplitByBy = truncated.split("/by");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidDeadline(partsSplitBySpace, partsSplitByBy);
        });

        assertEquals("Missing date indicator for deadline. Please add a /by <date/time>.", exception.getMessage());

        input = "deadline  /by  ";
        truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] partsSplitBySpace2 = truncated.split(" ");
        String[] partsSplitByBy2 = truncated.split("/by");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidDeadline(partsSplitBySpace2, partsSplitByBy2);
        });

        assertEquals("We seem to have forgotten the description and date for our deadline. "
                + "Do specify them.", exception.getMessage());

        input = "deadline   /by 10/3/2020 2000";
        truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] partsSplitBySpace3 = truncated.split(" ");
        String[] partsSplitByBy3 = truncated.split("/by");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidDeadline(partsSplitBySpace3, partsSplitByBy3);
        });

        assertEquals("We seem to have forgotten the description for our deadline. "
                + "Do specify it.", exception.getMessage());

        input = "deadline dream /by   ";
        truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] partsSplitBySpace4 = truncated.split(" ");
        String[] partsSplitByBy4 = truncated.split("/by");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidDeadline(partsSplitBySpace4, partsSplitByBy4);
        });

        assertEquals("We seem to have forgotten the date for our deadline. "
                + "Do specify it.", exception.getMessage());
    }

    @Test
    public void checkValidEvent_validEvent_success() throws OwenException {
        String input = "event eat death /from 2/12/2019 1800 /to 2/12/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace = truncated.split(" ");
        String[] truncatedSplitByFromTo = truncated.split("/from|/to", 3);
        assertDoesNotThrow(() -> {
            Parser.checkValidEvent(truncatedSplitBySpace, truncatedSplitByFromTo);
        });
    }

    @Test
    public void checkValidEvent_invalidEvent_throwsException() throws OwenException {
        String input = "event eat death  2/12/2019 1800 2/12/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace2 = truncated.split(" ");
        String[] truncatedSplitByFromTo2 = truncated.split("/from|/to", 3);
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace2, truncatedSplitByFromTo2);
        });

        assertEquals("Missing start and end date indicators. "
                + "Please add a /from <date/time> and /to <date/time>.",
                 exception.getMessage());

        input = "event eat death /from 2/12/2019 1800 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace3 = truncated.split(" ");
        String[] truncatedSplitByFromTo3 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace3, truncatedSplitByFromTo3);
        });

        assertEquals("Missing end date indicator. Please add a /to <date/time>.", exception.getMessage());

        input = "event eat death  2/12/2019 1800 /to 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace4 = truncated.split(" ");
        String[] truncatedSplitByFromTo4 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace4, truncatedSplitByFromTo4);
        });

        assertEquals("Missing start date indicator. Please add a /from <date/time>.", exception.getMessage());

        input = "event   /from   /to   ";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace5 = truncated.split(" ");
        String[] truncatedSplitByFromTo5 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace5, truncatedSplitByFromTo5);
        });

        assertEquals("We seem to have forgotten the description, start date and end date for our event. "
                + "Do specify them.", exception.getMessage());

        input = "event eat death /from  /to  ";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace6 = truncated.split(" ");
        String[] truncatedSplitByFromTo6 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace6, truncatedSplitByFromTo6);
        });
        assertEquals("We seem to have forgotten the start date and end date for our event. "
                + "Do specify them.", exception.getMessage());

        input = "event  /from    /to 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace7 = truncated.split(" ");
        String[] truncatedSplitByFromTo7 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace7, truncatedSplitByFromTo7);
        });
        assertEquals("We seem to have forgotten the description and start date for our event. "
                + "Do specify them.", exception.getMessage());

        input = "event   /from 2/12/2020 1800 /to   ";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace8 = truncated.split(" ");
        String[] truncatedSplitByFromTo8 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace8, truncatedSplitByFromTo8);
        });
        assertEquals("We seem to have forgotten the description and end date for our event. "
                + "Do specify them.", exception.getMessage());

        input = "event project meeting /from 2/12/2020 1800 /to ";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace9 = truncated.split(" ");
        String[] truncatedSplitByFromTo9 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace9, truncatedSplitByFromTo9);
        });
        assertEquals("We seem to have forgotten the end date for our event. "
                + "Do specify it.", exception.getMessage());

        input = "event project meeting /from  /to 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace10 = truncated.split(" ");
        String[] truncatedSplitByFromTo10 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace10, truncatedSplitByFromTo10);
        });
        assertEquals("We seem to have forgotten the start date for our event. "
                + "Do specify it.", exception.getMessage());

        input = "event   /from 2/12/2020 1800  /to 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] truncatedSplitBySpace11 = truncated.split(" ");
        String[] truncatedSplitByFromTo11 = truncated.split("/from|/to", 3);
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(truncatedSplitBySpace11, truncatedSplitByFromTo11);
        });
        assertEquals("We seem to have forgotten the description for our event. "
                + "Do specify it.", exception.getMessage());
    }

    @Test
    public void createDeadline_validDateFormat_success() throws OwenException {
        String input = "deadline dream /by 10/3/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        parts = truncated.split("/by");
        Parser.trimStringArray(parts);
        Deadline deadline = Parser.createDeadline(parts);
        assertEquals(Deadline.class, deadline.getClass());
    }

    @Test
    public void createDeadline_invalidDateFormat_throwsException() throws OwenException {
        String input = "deadline dream /by 10 Mar 2020 8pm";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        parts = truncated.split("/by");
        Parser.trimStringArray(parts);
        String[] failedParts = parts;
        Exception exception = assertThrows(OwenException.class, () -> {
            Deadline deadline = Parser.createDeadline(failedParts);
        });

        assertEquals("Given datetime is in wrong format. "
                + "Please use d/M/yyyy HHmm", exception.getMessage());
    }

    @Test
    public void createEvent_validDateFormat_success() throws OwenException {
        String input = "event eat death /from 2/12/2019 1800 /to 2/12/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        parts = truncated.split("/from | /to");
        Parser.trimStringArray(parts);
        Event event = Parser.createEvent(parts);
        assertEquals(Event.class, event.getClass());
    }

    @Test
    public void createEvent_invalidDateFormat_throwsException() throws OwenException {
        String input = "event eat death /from 2 Dec 2019 6pm /to 2 Dec 2020 8pm";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        parts = truncated.split("/from | /to");
        Parser.trimStringArray(parts);
        String[] failedParts = parts;
        Exception exception = assertThrows(OwenException.class, () -> {
            Event event = Parser.createEvent(failedParts);
        });

        assertEquals("Given datetime is in wrong format. "
                + "Please use d/M/yyyy HHmm", exception.getMessage());
    }

    @Test
    public void convertStringToLocalDateTime_validFormat_success() {
        LocalDateTime dateTime = Parser.convertStringToLocalDateTime("2/12/2019 1800");
        assertNotNull(dateTime, "DateTime should be parsed successfully");
        assertEquals(2019, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(2, dateTime.getDayOfMonth());

    }

    @Test
    public void convertStringToLocalDateTime_invalidFormat_returnsNull() {
        LocalDateTime dateTime = Parser.convertStringToLocalDateTime("invalid date");
        assertNull(dateTime);
    }

    @Test
    public void convertLocalDateToPattern_validFormat_success() {
        LocalDateTime dateTime = Parser.convertStringToLocalDateTime("2/12/2019 1800");
        String dateString = Parser.convertLocalDateToPattern(dateTime);

        assertEquals("2/12/2019 1800", dateString);
    }

    @Test
    public void checkValidMarkOrDelete_invalidMarkOrDelete_throwsException() throws OwenException {
        String[] parts = "mark".split(" ");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidMarkOrDelete(parts);
        });

        assertEquals("Please specify an index. Try again.", exception.getMessage());

        String[] parts2 = "mark 1 2".split(" ");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidMarkOrDelete(parts2);
        });

        assertEquals("Too many parameters for a mark or delete. Limit it to just one index.", exception.getMessage());
    }

    @Test
    public void checkValidTag_invalidTag_throwsException() throws OwenException {
        String[] parts = "tag".split(" ");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidTag(parts);
        });

        assertEquals("Please specify an index and a tag. Do try again.", exception.getMessage());

        String[] parts2 = "tag 1".split(" ");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidTag(parts2);
        });

        assertEquals("Please specify a tag. Please try again.", exception.getMessage());
    }
}
