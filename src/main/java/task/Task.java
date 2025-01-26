package task;

/**
 * Represents a task
 */
public class Task {

    /** Description of the task */
    private String description;

    /** Status of the task */
    private boolean isDone;

    /**
     * Constructor for Task
     *
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructor for Task
     *
     * @param description Description of the task
     * @param isDone Status of the task
     */
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

    /**
     * Returns the string representation of the task
     *
     * @return String representation of the task
     */
    @Override
    public String toString() {
        return getDoneIcon() + " " + getDescription();
    }

    /**
     * Returns the string representation of the task in data format
     *
     * @return String representation of the task in data format
     */
    public String convertToDataFormat() {
        String doneStatus = isDone ? "1" : "0";
        return doneStatus + " | " +  getDescription();
    }
}
