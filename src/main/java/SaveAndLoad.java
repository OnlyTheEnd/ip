import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path FILE_PATH = DATA_DIR.resolve("tasks.txt");


    private static void ensureStorageExists() throws IOException {
        if (!Files.exists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }
        if (!Files.exists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
        }
    }
    public static void save(List<Task> tasks) {
        try {
            // ensure directory exists
            ensureStorageExists();
            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(t.toString()); // save as plain string
            }
            Files.write(FILE_PATH, lines);
        } catch (Exception e) {
            System.out.println("    Failed to save tasks: " + e.getMessage());
        }


    }

    public static void load(List<Task> tasks) {
        try {
            ensureStorageExists();

            tasks.clear();
            List<String> lines = Files.readAllLines(FILE_PATH);
            for (String line : lines) {
                Task t = Task.fromString(line);
                if (t != null) tasks.add(t);
            }

        } catch (Exception e) {
            System.out.println("    Failed to load tasks: " + e.getMessage());
        }
    }

}

