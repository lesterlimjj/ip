package owen.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

import owen.exception.OwenException;
import owen.parser.Parser;
import owen.task.Deadline;
import owen.task.Event;
import owen.task.Task;
import owen.task.TaskList;
import owen.task.Todo;


/**
 * Represents the storage that handles the writing and loading of data.
 */
public class Storage {

    /** Path to the task list data file. */
    private static final Path TASKLIST_PATH = Paths.get("./", "data", "taskList.txt");


    /**
     * Reads the text file data, parses the data into tasks and adds the tasks to the task list.
     * If the file does not exist, it creates a new file.
     *
     * @param taskList The tasklist to add the tasks to.
     * @throws OwenException If there is an error reading the file.
     */
    public void loadTaskListData(TaskList taskList) {
        try {
            createTaskListFileAndDirectoryIfNeeded();
            List<String> lines = Files.readAllLines(TASKLIST_PATH);
            for (int i = 0; i < lines.size(); i++) {
                String[] dataSegments = splitAndTrimLine(lines.get(i));
                switch (dataSegments[0]) {
                case "T":
                    addTodoFromDataSegment(dataSegments, taskList);
                    break;
                case "D":
                    addDeadlineFromDataSegment(dataSegments, taskList);
                    break;
                case "E":
                    addEventFromDataSegment(dataSegments, taskList);
                    break;
                default:
                    throw new OwenException("invalid data format in file");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the task list file and directory if they do not exist.
     *
     * @throws IOException If there is an error creating the file or directory.
     */
    public void createTaskListFileAndDirectoryIfNeeded() throws IOException {
        if (!Files.exists(TASKLIST_PATH.getParent())) {
            Files.createDirectory(TASKLIST_PATH.getParent());
        }

        if (!Files.exists(TASKLIST_PATH)) {
            Files.createFile(TASKLIST_PATH);
        }
    }

    /**
     * Splits a line into segments and trims each segment.
     *
     * @param line The line to split and trim.
     * @return An array of trimmed segments.
     */
    public String[] splitAndTrimLine(String line) {
        String[] parts = line.split("\\|");
        for (int j = 0; j < parts.length; j++) {
            parts[j] = parts[j].trim();
        }
        return parts;
    }

    /**
     * Adds a todo task from a data segment.
     *
     * @param dataSegments The data segments containing the Todo information.
     * @param taskList The task list to add the task to.
     */
    public void addTodoFromDataSegment(String[] dataSegments, TaskList taskList) {
        boolean isDone = dataSegments[1].equals("1");
        String description = dataSegments[2];
        String[] tags = dataSegments[3].split(" ");

        Todo loadedTodo = new Todo(description, isDone);
        addTagsToTaskFromDataSegment(dataSegments[3], loadedTodo);
        taskList.addTask(loadedTodo);
    }

    /**
     * Adds a deadline task from a data segment.
     *
     * @param dataSegments The data segments containing the Deadline information.
     * @param taskList The task list to add the task to.
     */
    public void addDeadlineFromDataSegment(String[] dataSegments, TaskList taskList) {
        boolean isDone = dataSegments[1].equals("1");
        String description = dataSegments[2];
        String date = dataSegments[4];

        LocalDateTime deadline = Parser.convertStringToLocalDateTime(date);

        Deadline loadedDeadline = new Deadline(description, isDone, deadline);
        addTagsToTaskFromDataSegment(dataSegments[3], loadedDeadline);
        taskList.addTask(loadedDeadline);
    }

    /**
     * Adds an event task from a data segment.
     *
     * @param dataSegments The data segments containing the Event information.
     * @param taskList The task list to add the task to.
     */
    public void addEventFromDataSegment(String[] dataSegments, TaskList taskList) {
        boolean isDone = dataSegments[1].equals("1");
        String description = dataSegments[2];
        String startDate = dataSegments[4].split("-")[0];
        String endDate = dataSegments[4].split("-")[1];

        LocalDateTime startDateTime = Parser.convertStringToLocalDateTime(startDate);
        LocalDateTime endDateTime = Parser.convertStringToLocalDateTime(endDate);

        Event loadedEvent = new Event(description, isDone, startDateTime, endDateTime);
        addTagsToTaskFromDataSegment(dataSegments[3], loadedEvent);
        taskList.addTask(loadedEvent);
    }

    /**
     * Adds tags to a task from a data segment.
     *
     * @param dataSegment The data segment containing the tags.
     * @param task The task to add the tags to.
     */
    public void addTagsToTaskFromDataSegment(String dataSegment, Task task) {
        String[] tags = dataSegment.split(" ");
        for (int i = 0; i < tags.length; i++) {
            if (!tags[i].trim().isEmpty()) {
                task.addTag(tags[i]);
            }
        }
    }

    /**
     * Overwrites the current tasklist data with the new tasklist data.
     * This method is used to update the tasklist data in the file when a task is deleted or modified.
     *
     * @param taskList The new tasklist to be written to the file.
     */
    public void overwriteTaskListData(List<Task> taskList) {
        StringBuilder linesToWrite = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = task.convertToDataFormat();
            linesToWrite.append(line);

            // the last added task should not have newline
            // since if appended to after overwrite, file format will break due to empty line
            if (i != taskList.size() - 1) {
                linesToWrite.append(System.lineSeparator());
            }
        }

        try {
            Files.writeString(TASKLIST_PATH, linesToWrite);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }

    /**
     * Appends a new task to the tasklist data.
     * This method is only used when adding a new task to the tasklist data in the file.
     *
     * @param task The new task to be added to the tasklist data.
     */
    public void appendToTasklistData(Task task) {
        String line = task.convertToDataFormat();
        try {
            // adding a newline for the first added task will break file format
            if (Files.size(TASKLIST_PATH) != 0) {
                line = "\n" + line;
            }

            Files.writeString(TASKLIST_PATH, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }
}
