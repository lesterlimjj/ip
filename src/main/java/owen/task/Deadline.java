package owen.task;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import owen.parser.Parser;

/**
 * Represents a task with a specified deadline.
 */
public class Deadline extends Task {

    /** The date and time of the deadline */
    private LocalDateTime date;

    /**
     * Constructs a deadline object with the specified description and date.
     *
     * @param description The description of the task.
     * @param date The date and time of the deadline.
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
        assert date != null : "Date cannot be null";
    }


    /**
     * Constructs a Deadline object with the specified description, completion status, and date.
     *
     * @param description The description of the deadline.
     * @param isDone The completion status of the deadline.
     * @param date  The date and time of the deadline.
     */
    public Deadline(String description, boolean isDone, LocalDateTime date) {
        super(description, isDone);
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[D]" + super.toString() + " (by: " + getDate().format(outputFormatter) + ")";
    }

    @Override
    public String convertToDataFormat() {
        return "D" + " | " + super.convertToDataFormat() + " | " + Parser.convertLocalDateToPattern(getDate());
    }

}
