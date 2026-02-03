import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    //Default save path
    private static Path data = Path.of("data");
    private static Path filePath = data.resolve("tasks.json");

    public Storage(String filePath) {
        String[] parts = filePath.split("/");
        data = Path.of(parts[0]);
        Storage.filePath = data.resolve(parts[1]);
    }

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();



    private static void ensureStorageExists() throws Exception {
        if (!Files.exists(data)) {
            Files.createDirectories(data);
        }
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, "[]"); // empty JSON array
        }
    }

    // Save a generic list of tasks to JSON
    public static void save(List<Task> tasks) {
        try {
            ensureStorageExists();
            String json = GSON.toJson(tasks);
            Files.writeString(filePath, json);
        } catch (Exception e) {
            System.out.println("    Failed to save tasks: " + e.getMessage());
        }
    }


    public static List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            ensureStorageExists();
            String json = Files.readString(filePath);
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
