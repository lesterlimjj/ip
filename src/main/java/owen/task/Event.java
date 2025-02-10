package owen.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import owen.parser.Parser;

/**
 * Represents a task with a start and end date.
 */
public class Event extends Task {

    /** Start date of the event. */
    private LocalDateTime startDate;

    /** End date of the event. */
    private LocalDateTime endDate;

    /**
     * Constructor for Event with specified description, start date, and end date.
     *
     * @param description Description of the event.
     * @param startDate Start date of the event.
     * @param endDate End date of the event.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for Event with specified description, status, start date, and end date.
     *
     * @param description Description of the event.
     * @param isDone Whether the event is done.
     * @param startDate Start date of the event.
     * @param endDate End date of the event.
     */
    public Event(String description, boolean isDone, LocalDateTime startDate, LocalDateTime endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
        assert startDate != null :  "Start date should not be null";
        assert endDate != null :  "End date should not be null";
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[E]" + super.toString() + " (from: " + getStartDate().format(outputFormatter) + " to: "
                + getEndDate().format(outputFormatter) + ")";
    }

    @Override
    public String convertToDataFormat() {
        String combinedDates = Parser.convertLocalDateToPattern(getStartDate()) + "-"
                + Parser.convertLocalDateToPattern(getEndDate());
        return "E" + " | " + super.convertToDataFormat() + " | " + combinedDates;
    }
}
