import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path taskListPath = Paths.get("./","data", "taskList.txt");

    public static void loadTasklistData(List<Task> taskList) {
        try {
            if (Files.exists(taskListPath)){
                List<String> lines = Files.readAllLines(taskListPath);
                for (int i = 0; i < lines.size(); i++) {
                    String [] parts = lines.get(i).split("\\|");
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
                            taskList.add(loadedTodo);
                            break;
                        case "D":
                            isDone = parts[1].equals("1");
                            description = parts[2];

                            Deadline loadedDeadline = new Deadline(description, isDone, parts[3]);
                            taskList.add(loadedDeadline);
                            break;
                        case "E":
                            isDone = parts[1].equals("1");
                            description = parts[2];
                            String startDate = parts[3].split("-")[0];
                            String endDate = parts[3].split("-")[1];
                            Event loadedEvent = new Event(description, isDone, startDate, endDate);
                            taskList.add(loadedEvent);
                            break;
                    }
                }
            } else {
                if (!Files.exists(taskListPath.getParent())) {
                    Files.createDirectory(taskListPath.getParent());
                }

                if (!Files.exists(taskListPath)) {
                    Files.createFile(taskListPath);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void overwriteTasklistData(List<Task> taskList) {
        List <String> linesToWrite = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String line = task.convertToDataFormat();
            linesToWrite.add(line);
        }

        try {
            Files.write(taskListPath,linesToWrite);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }

    public static void appendToTasklistData(Task task, List<Task> taskList) {
        String line = task.convertToDataFormat();
        try {
            Files.writeString(taskListPath, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }
}
