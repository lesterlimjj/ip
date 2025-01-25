package task;

import parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDateTime date;
    private static final String [] localDateTimePatterns = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};

    public Deadline(String description, LocalDateTime date){
        super(description);
        this.date = date;
    }

    public Deadline(String description, boolean isDone, LocalDateTime date){
        super(description, isDone);
        this.date = date;
    }

    public LocalDateTime getDate(){
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
