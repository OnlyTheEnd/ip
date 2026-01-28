import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path FILE_PATH = DATA_DIR.resolve("tasks.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();



    private static void ensureStorageExists() throws Exception {
        if (!Files.exists(DATA_DIR)) {
            Files.createDirectories(DATA_DIR);
        }
        if (!Files.exists(FILE_PATH)) {
            Files.writeString(FILE_PATH, "[]"); // empty JSON array
        }
    }

    // Save a generic list of tasks to JSON
    public static void save(List<Task> tasks) {
        try {
            ensureStorageExists();
            String json = GSON.toJson(tasks);
            Files.writeString(FILE_PATH, json);
        } catch (Exception e) {
            System.out.println("    Failed to save tasks: " + e.getMessage());
        }
    }


    public static List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            ensureStorageExists();
            String json = Files.readString(FILE_PATH);
            Gson gson = new Gson();

            JsonArray array = gson.fromJson(json, JsonArray.class);
            for (var elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String type = obj.get("type").getAsString();

                Task task = switch (type) {
                    case "ToDo" -> gson.fromJson(obj, ToDo.class);
                    case "Deadline" -> gson.fromJson(obj, Deadline.class);
                    case "Event" -> gson.fromJson(obj, Event.class);
                    default -> null;
                };

                if (task != null) tasks.add(task);
            }

        } catch (Exception e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
        return tasks;
    }
}
