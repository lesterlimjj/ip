package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

import exception.OwenException;
import parser.Parser;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import task.Todo;


public class Storage {
    private static final Path TASKLIST_PATH = Paths.get("./", "data", "taskList.txt");

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

    public void appendToTasklistData(Task task) {
        String line = task.convertToDataFormat();
        try {
            Files.writeString(TASKLIST_PATH, "\n" + line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }
}
