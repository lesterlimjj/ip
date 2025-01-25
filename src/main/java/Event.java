import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private static final String [] localDateTimePatterns = {"d/M/yyyy HHmm", "M/d/yyyy HHmm"};

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = processLocalDateTime(startDate);
        this.endDate = processLocalDateTime(endDate);
    }

    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description,isDone);
        this.startDate = processLocalDateTime(startDate);
        this.endDate = processLocalDateTime(endDate);
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
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
        String combinedDates = startDate + "-" + endDate;
        return "E" + " | " + super.convertToDataFormat() + " | " + combinedDates;
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
