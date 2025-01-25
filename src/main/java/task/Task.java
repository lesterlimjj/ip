package task;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
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

    public void setAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return getDoneIcon() + " " + getDescription();
    }

    public String convertToDataFormat() {
        String doneStatus = isDone ? "1" : "0";
        return doneStatus + " | " +  getDescription();
    }
}
