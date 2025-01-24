public class Deadline extends Task{
    private String date;

    public Deadline(String description, String date){
        super(description);
        this.date = date;
    }

    public Deadline(String description, boolean isDone, String date){
        super(description, isDone);
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + getDate() + ")";
    }

}
