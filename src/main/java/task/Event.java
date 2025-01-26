package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import parser.Parser;


public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String description, boolean isDone, LocalDateTime startDate, LocalDateTime endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
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
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("m/dd/yyyy HHmm");
        String combinedDates = Parser.convertLocalDateToPattern(getStartDate()) + "-"
                + Parser.convertLocalDateToPattern(getEndDate());
        return "E" + " | " + super.convertToDataFormat() + " | " + combinedDates;
    }


}
