import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

public class Deadline extends Task{
    private LocalDateTime date;
    private static final String [] localDateTimePatterns = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};

    public Deadline(String description, String date){
        super(description);
        this.date = processLocalDateTime(date);
    }

    public Deadline(String description, boolean isDone, String date){
        super(description, isDone);
        this.date = processLocalDateTime(date);
    }

    public LocalDateTime getDate(){
        return date;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
        return "[D]" + super.toString() + "(by: " + getDate().format(outputFormatter) + ")";
    }

    @Override
    public String convertToDataFormat() {
        return "D" + " | " + super.convertToDataFormat() + " | " + getDate();
    }

    public LocalDateTime processLocalDateTime(String dateString) {
        LocalDateTime date = null;
        for (int i = 0; i < localDateTimePatterns.length; i++) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(localDateTimePatterns[i]);
            try {
                date = LocalDateTime.parse(dateString, dateFormatter);
                break;  // Exit the loop once the date is successfully parsed
            } catch (DateTimeParseException e) {
                // do nothing and check for next pattern
            }
        }
        return date;
    }

}
