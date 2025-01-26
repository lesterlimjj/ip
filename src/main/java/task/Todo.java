package task;

/**
 * Represents a task that is a Todo.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo with specified description.
     *
     * @param description Description of the Todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructor for Todo with specified description and isDone status.
     *
     * @param description Description of the Todo.
     * @param isDone      Whether the Todo is done.
     */
    public Todo(String description, Boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String convertToDataFormat() {
        return "T" + " | " + super.convertToDataFormat();
    }
}
