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
        String[] parts = truncated.split(" ");
        assertDoesNotThrow(() -> {
            Parser.checkValidDeadline(parts);
        });
    }

    @Test
    public void checkValidDeadline_invalidDeadline_throwsException() throws OwenException {
        String input = "deadline dream 10/3/2020 2000";
        String truncated = input.replaceFirst(AddDeadlineCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidDeadline(parts);
        });

        assertEquals("We cannot find a date. Please add a /by <date/time>", exception.getMessage());
    }

    @Test
    public void checkValidEvent_validEvent_success() throws OwenException {
        String input = "event eat death /from 2/12/2019 1800 /to 2/12/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts = truncated.split(" ");
        assertDoesNotThrow(() -> {
            Parser.checkValidEvent(parts);
        });
    }

    @Test
    public void checkValidEvent_invalidEvent_throwsException() throws OwenException {
        String input = "event eat death  2/12/2019 1800 2/12/2020 2000";
        String truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts1 = truncated.split(" ");
        Exception exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(parts1);
        });

        assertEquals("Missing start and end date. "
                + "Please add a /from <date/time> and add a /to <date/time>", exception.getMessage());

        input = "event eat death /from 2/12/2019 1800 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts2 = truncated.split(" ");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(parts2);
        });

        assertEquals("Missing end date. Please add a /to <date/time>", exception.getMessage());

        input = "event eat death  2/12/2019 1800 /to 2/12/2020 2000";
        truncated = input.replaceFirst(AddEventCommand.KEY_WORD + " ", "");
        String[] parts3 = truncated.split(" ");
        exception = assertThrows(OwenException.class, () -> {
            Parser.checkValidEvent(parts3);
        });

        assertEquals("Missing start date. Please add a /from <date/time>", exception.getMessage());
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
                + "Please use M/d/yyyy HHmm or d/M/yyyy HHmm", exception.getMessage());
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
                + "Please use M/d/yyyy HHmm or d/M/yyyy HHmm", exception.getMessage());
    }

    @Test
    public void convertStringToLocalDateTime_validFormat_success() {
        LocalDateTime dateTime = Parser.convertStringToLocalDateTime("2/12/2019 1800");
        assertNotNull(dateTime, "DateTime should be parsed successfully");
        assertEquals(2019, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(2, dateTime.getDayOfMonth());

        dateTime = Parser.convertStringToLocalDateTime("3/31/2019 1800");
        assertNotNull(dateTime, "DateTime should be parsed successfully");
        assertEquals(2019, dateTime.getYear());
        assertEquals(3, dateTime.getMonthValue());
        assertEquals(31, dateTime.getDayOfMonth());
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

}
