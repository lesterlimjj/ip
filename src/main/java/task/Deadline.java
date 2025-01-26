package task;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import parser.Parser;

public class Deadline extends Task {
    private LocalDateTime date;

    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
    }

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
