public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description,isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + getStartDate() + " to: " + getEndDate() + ")";
    }

}
