package veigar.tools;

import java.util.List;

import veigar.task.Deadline;
import veigar.task.Event;
import veigar.task.Task;


/**
 * Operates on taskList.
 */
public class TaskList {
    private List<Task> taskList;
    private final StringBuilder stringBuilder = new StringBuilder();

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int getListSize() {
        return taskList.size();
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    /**
     * Adds Task T into the taskList.
     * @param task A Task.
     */
    public void addTask(Task task) {
        taskList.add(task);
        saveChanges();
    }

    /**
     * Removes a Task from taskList at index i.
     * @param i Index of removed task.
     */
    public void removeTask(int i) {
        taskList.remove(i);
        saveChanges();
    }

    /**
     * Unmarks a Task from taskList at index i
     * @param i Index of task to be unmarked.
     */
    public void markUndone(int i) {
        taskList.get(i).markUndone();
        saveChanges();
    }

    /**
     * Marks a Task from taskList at index i
     * @param i Index of task to be marked.
     */
    public void markDone(int i) {
        taskList.get(i).markDone();
        saveChanges();
    }
    /**
     * Displays the entire taskList as a String, each line representing one Task with a space after each one.
     * Displays empty message if size of taskList is 0.
     */
    public String listTasks() {
        if (!taskList.isEmpty()) {
            for (int i = 0; i < taskList.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(taskList.get(i)).append("\n");
            }
        } else {
            stringBuilder.append("DONT DEAD OPEN INSIDE LIST IS 0");
        }
        String listString = stringBuilder.toString();
        stringBuilder.setLength(0);
        return listString;
    }

    /**
     * Displays the specified tasks which matches the queryDate date component.
     * @param queryDate Date of Task to be matched.
     */
    public String matchTasks(String queryDate) {
        boolean isFound = false;
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            String taskDate;
            if (t instanceof Deadline d) {
                taskDate = d.getBy().split(",")[0].trim();
            } else if (t instanceof Event e) {
                taskDate = e.getToDate().split(",")[0].trim();
            } else {
                continue; // skip other task types
            }
            if (taskDate.equals(queryDate)) {
                stringBuilder.append((i + 1)).append(". ").append(t).append("\n");
                isFound = true;
            }
        }
        String showString = stringBuilder.toString();
        stringBuilder.setLength(0);
        if (!isFound) {
            showString = "tasks isFound on " + queryDate;
        }
        return showString;
    }
    /**
     * Displays the specified tasks which description matches the queryString.
     * @param queryString String of Task Description to be matched.
     */
    public String findTasks(String queryString) {
        boolean isFound = false;
        for (int i = 0; i < taskList.size(); i++) {
            String taskDescription = taskList.get(i).getDescription();
            if (taskDescription.contains(queryString)) {
                stringBuilder.append((i + 1)).append(". ").append(taskList.get(i)).append("\n");
                isFound = true;
            }
        }
        String findString = stringBuilder.toString();
        if (!isFound) {
            findString = "No tasks isFound matching " + queryString;
        }
        stringBuilder.setLength(0);
        return findString;
    }

    /**
     * Saves after any updates to taskList.
     */
    private void saveChanges() {
        Storage.save(taskList);
    }
}

