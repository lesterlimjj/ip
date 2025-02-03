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
    public void loadTasklistData(TaskList taskList) {
        try {
            if (Files.exists(TASKLIST_PATH)) {
                List<String> lines = Files.readAllLines(TASKLIST_PATH);
                for (int i = 0; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split("\\|");
                    for (int j = 0; j < parts.length; j++) {
                        parts[j] = parts[j].trim();
                    }
                    boolean isDone;
                    String description;

                    switch (parts[0]) {
                    case "T":
                        isDone = parts[1].equals("1");
                        description = parts[2];
                        Todo loadedTodo = new Todo(description, isDone);
                        taskList.addTask(loadedTodo);
                        break;
                    case "D":
                        isDone = parts[1].equals("1");
                        description = parts[2];
                        LocalDateTime date = Parser.processLocalDateTime(parts[3]);
                        Deadline loadedDeadline = new Deadline(description, isDone, date);
                        taskList.addTask(loadedDeadline);
                        break;
                    case "E":
                        isDone = parts[1].equals("1");
                        description = parts[2];
                        String startDate = parts[3].split("-")[0];
                        String endDate = parts[3].split("-")[1];
                        LocalDateTime startDateTime = Parser.processLocalDateTime(startDate);
                        LocalDateTime endDateTime = Parser.processLocalDateTime(endDate);
                        Event loadedEvent = new Event(description, isDone, startDateTime, endDateTime);
                        taskList.addTask(loadedEvent);
                        break;
                    default:
                        throw new OwenException("invalid data format in file");
                    }
                }
            } else {
                if (!Files.exists(TASKLIST_PATH.getParent())) {
                    Files.createDirectory(TASKLIST_PATH.getParent());
                }

                if (!Files.exists(TASKLIST_PATH)) {
                    Files.createFile(TASKLIST_PATH);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Overwrites the current tasklist data with the new tasklist data.
     * This method is used to update the tasklist data in the file when a task is deleted or modified.
     *
     * @param taskList the new tasklist to be written to the file.
     */
    public void overwriteTasklistData(List<Task> taskList) {
        StringBuilder linesToWrite = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = task.convertToDataFormat();
            linesToWrite.append(line);
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
     * @param task the new task to be added to the tasklist data.
     */
    public void appendToTasklistData(Task task) {
        String line = task.convertToDataFormat();
        try {
            if (Files.size(TASKLIST_PATH) != 0) {
                line = "\n" + line;
            }

            Files.writeString(TASKLIST_PATH, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }
}
