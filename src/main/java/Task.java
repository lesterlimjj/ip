public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDoneIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    public void setAsDone() {
        this.isDone = true;
    }
    
}
