package owen.task;

import java.util.ArrayList;

/**
 * Represents a task.
 */
public class Task {

    /** Description of the task */
    private String description;

    /** Status of the task */
    private boolean isDone;
    private ArrayList<String> tags = new ArrayList<String>();

    /**
     * Constructs a task object with specified description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Constructs a task object with specified description and status.
     *
     * @param description Description of the task.
     * @param isDone Status of the task.
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
     * Returns the string representation of the task.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return getDoneIcon() + " " + getDescription() + " " + convertTagsToString();
    }

    /**
     * Returns the string representation of the task in data format.
     *
     * @return String representation of the task in data format.
     */
    public String convertToDataFormat() {
        String doneStatus = isDone ? "1" : "0";
        return doneStatus + " | " + getDescription() + " | " + convertTagsToDataFormat();
    }

    /**
     * Adds a tag to the list of tags for a task.
     *
     * @param tag The provided tag.
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Converts the list of tags to a string.
     *
     * @return The string representation of the tags.
     */
    public String convertTagsToString() {
        String tagsString = "";
        for (int i = 0; i < tags.size(); i++) {
            tagsString += "#" + tags.get(i);

            if (i != tags.size() - 1) {
                tagsString += " ";
            }
        }
        return tagsString;
    }

    /**
     * Converts the list of tags to a string in data format.
     *
     * @return The string representation of the tags in data format.
     */
    public String convertTagsToDataFormat() {
        String tagsString = "";
        for (int i = 0; i < tags.size(); i++) {
            tagsString += tags.get(i);

            if (i != tags.size() - 1) {
                tagsString += " ";
            }
        }
        return tagsString;
    }
}
