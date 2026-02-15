package veigar.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import veigar.task.DeadlineTask;
import veigar.task.EventTask;
import veigar.task.Task;
import veigar.task.ToDoTask;


/**
 *
 */
public class Storage {
    //Default save path
    private static Path data = Path.of("data");
    private static Path filePath = data.resolve("base.json");



    /**
     * Instantiates GsonBuilder to help build save file as a .json file.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Sets the destination of save file.
     * @param filePath Destination of save file.
     */
    public static void setPath(String filePath) {
        String[] parts = filePath.split("/");
        data = Path.of(parts[0]);
        Storage.filePath = data.resolve(parts[1]);
    }

    /**
     * Checks if directory is there, else creates a new directory.
     * @throws IOException if directory not found
     */
    private static void ensureStorageExists() throws IOException {
        if (!Files.exists(data)) {
            Files.createDirectories(data);
        }
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, "[]"); // empty JSON array
        }
    }

    /**
     * Save a generic list of tasks to JSON.
     * @param tasks Saves the taskList to a JSON file specified under filePath.
     */
    public static void save(List<Task> tasks) {
        try {
            ensureStorageExists();
            String json = GSON.toJson(tasks);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            System.out.println("    Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Loads a generic list of tasks from JSON.
     * @return the Task List obtained from converting JSON file found at filePath.
     */
    public static List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            ensureStorageExists();
            String json = Files.readString(filePath);

            JsonArray array = GSON.fromJson(json, JsonArray.class);
            for (var elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String type = obj.get("type").getAsString();

                Task task = switch (type) {
                case "veigar.task.ToDo" -> GSON.fromJson(obj, ToDoTask.class);
                case "veigar.task.Deadline" -> GSON.fromJson(obj, DeadlineTask.class);
                case "veigar.task.Event" -> GSON.fromJson(obj, EventTask.class);
                default -> null;
                };

                if (task != null) {
                    tasks.add(task);
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
        return tasks;
    }

}
