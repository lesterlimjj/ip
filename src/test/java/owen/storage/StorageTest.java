package owen.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import owen.parser.Parser;
import owen.task.Deadline;
import owen.task.Event;
import owen.task.Task;
import owen.task.TaskList;
import owen.task.Todo;

public class StorageTest {
    private static final Path TEST_FILE_PATH = Paths.get("./", "data", "taskList.txt");

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH); // Clean up before each test
        if (!Files.exists(TEST_FILE_PATH.getParent())) {
            Files.createDirectory(TEST_FILE_PATH.getParent());
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH); // Clean up after each test
    }

    @Test
    public void loadTaskListData_fileExists_validData() throws IOException {
        String data = "T | 1 | Sample Todo\n"
                + "D | 0 | Submit Assignment | 3/12/2019 1800\n"
                + "E | 1 | Meeting | 3/12/2019 1800-3/12/2019 2000";
        Files.writeString(TEST_FILE_PATH, data);
        Storage storage = new Storage();
        TaskList taskList = new TaskList();
        storage.loadTaskListData(taskList);
        assertEquals(3, taskList.getTaskList().size());
        assertEquals(Todo.class, taskList.getTaskList().get(0).getClass());
        assertEquals(Deadline.class, taskList.getTaskList().get(1).getClass());
        assertEquals(Event.class, taskList.getTaskList().get(2).getClass());
    }

    @Test
    public void loadTaskListData_fileNotExists_fileCreated() throws IOException {
        Storage storage = new Storage();
        TaskList taskList = new TaskList();
        storage.loadTaskListData(taskList);

        assertTrue(Files.exists(TEST_FILE_PATH));
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    public void overwriteTaskListData_validTasks_fileUpdated() throws IOException {
        Storage storage = new Storage();
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("Sample Todo", true));
        taskList.addTask(new Deadline("Submit Assignment", false,
                Parser.convertStringToLocalDateTime("3/12/2019 1800")));
        taskList.addTask(new Event("Meeting", true,
                Parser.convertStringToLocalDateTime("3/12/2019 1800"),
                Parser.convertStringToLocalDateTime("3/12/2019 2000")));

        storage.overwriteTaskListData(taskList.getTaskList());

        List<String> lines = Files.readAllLines(TEST_FILE_PATH);
        assertEquals(3, lines.size());
        assertEquals("T | 1 | Sample Todo", lines.get(0));
        assertEquals("D | 0 | Submit Assignment | 3/12/2019 1800", lines.get(1));
        assertEquals("E | 1 | Meeting | 3/12/2019 1800-3/12/2019 2000", lines.get(2));
    }

    @Test
    public void appendToTasklistData_validTask_taskAppended() throws IOException {
        Storage storage = new Storage();
        String initialData = "T | 1 | Sample Todo";
        Files.writeString(TEST_FILE_PATH, initialData);

        Task newTask = new Deadline("Submit Assignment", false,
                Parser.convertStringToLocalDateTime("3/12/2019 1800"));

        storage.appendToTasklistData(newTask);

        List<String> lines = Files.readAllLines(TEST_FILE_PATH);
        assertEquals(2, lines.size());
        assertEquals("T | 1 | Sample Todo", lines.get(0));
        assertEquals("D | 0 | Submit Assignment | 3/12/2019 1800", lines.get(1));
    }

    @Test
    public void loadTaskListData_invalidFileContent_throwsRuntimeException() throws IOException {
        Storage storage = new Storage();
        TaskList taskList = new TaskList();
        String invalidData = "Invalid | Data | Format";
        Files.writeString(TEST_FILE_PATH, invalidData);

        // Act & Assert: Exception expected
        assertThrows(RuntimeException.class, () -> storage.loadTaskListData(taskList));
    }
}
