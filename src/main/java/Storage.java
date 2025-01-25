import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path tasklistPath = Paths.get("./","data", "tasklist.txt");

    public static void loadTasklist(List<Task> tasklist) {
        try {
            if (Files.exists(tasklistPath)){
                List<String> lines = Files.readAllLines(tasklistPath);
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
                            tasklist.add(loadedTodo);
                            break;
                        case "D":
                            isDone = parts[1].equals("1");
                            description = parts[2];

                            Deadline loadedDeadline = new Deadline(description, isDone, parts[3]);
                            tasklist.add(loadedDeadline);
                            break;
                        case "E":
                            isDone = parts[1].equals("1");
                            description = parts[2];
                            String startDate = parts[3].split("-")[0];
                            String endDate = parts[3].split("-")[1];
                            Event loadedEvent = new Event(description, isDone, startDate, endDate);
                            tasklist.add(loadedEvent);
                            break;
                    }
                }
            } else {
                if (!Files.exists(tasklistPath.getParent())) {
                    Files.createDirectory(tasklistPath.getParent());
                }

                if (!Files.exists(tasklistPath)) {
                    Files.createFile(tasklistPath);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void overwriteTasklist(List<Task> tasklist) {
        List <String> linesToWrite = new ArrayList<>();
        for (int i = 0; i < tasklist.size(); i++) {
            Task task = tasklist.get(i);
            String line = task.convertToDataFormat();
            linesToWrite.add(line);
        }

        try {
            Files.write(tasklistPath,linesToWrite);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }

    public static void appendToTasklist(Task task, List<Task> tasklist) {
        String line = task.convertToDataFormat();
        try {
            Files.writeString(tasklistPath, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("We encountered an error while saving...");
        }
    }
}
