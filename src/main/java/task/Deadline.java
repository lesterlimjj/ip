package task;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import parser.Parser;

/**
 * Represents a task with a specified deadline.
 */
public class Deadline extends Task {

    /** The date and time of the deadline. */
    private LocalDateTime date;

    /**
     * Constructs a Deadline object with the specified description and date.
     *
     * @param description the description of the task
     * @param date the date and time of the deadline
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
    }


    /**
     * Constructs a Deadline object with the specified description, completion status, and date.
     *
     * @param description the description of the task
     * @param isDone the completion status of the deadline
     * @param date  the date and time of the deadline
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
