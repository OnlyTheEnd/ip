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

    public Task getTask(int i) throws IndexOutOfBoundsException {
        try {
            return taskList.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("    Index out of bounds");
        }
        return taskList.get(0);
    }

    /**
     * Adds Task T into the taskList.
     * @param task A Task.
     */
    public void addTask(Task task) {
        taskList.add(task);
        afterChange();
    }

    /**
     * Removes a Task from taskList at index i.
     * @param i Index of removed task.
     */
    public void removeTask(int i) {
        taskList.remove(i);
        afterChange();
    }

    public void markUndone(int i) {
        taskList.get(i).markUndone();
        afterChange();
    }

    public void markDone(int i) {
        taskList.get(i).markDone();
        afterChange();
    }
    /**
     * Displays the entire taskList as a String, each line representing one Task with a space after each one.
     * Displays empty message if size of taskList is 0.
     */
    public String listTasks() {
        if (!taskList.isEmpty()) {
            for (int i = 0; i < taskList.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(taskList.get(i));
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
    public String showTasks(String queryDate) {
        boolean found = false;
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
                stringBuilder.append((i + 1)).append(". ").append(t);
                found = true;
            }
        }
        String showString = stringBuilder.toString();
        stringBuilder.setLength(0);
        if (!found) {
            showString = "tasks found on " + queryDate;
        }
        return showString;
    }
    /**
     * Displays the specified tasks which description matches the queryString.
     * @param queryString String of Task Description to be matched.
     */
    public String findTasks(String queryString) {
        boolean found = false;
        for (int i = 0; i < taskList.size(); i++) {
            String taskDescription = taskList.get(i).getDescription();
            if (taskDescription.contains(queryString)) {
                stringBuilder.append((i + 1)).append(". ").append(taskList.get(i));
                found = true;
            }
        }
        String findString = stringBuilder.toString();
        stringBuilder.setLength(0);
        if (!found) {
            findString = "No tasks found matching " + queryString;
        }
        return findString;
    }

    /**
     * Saves after any updates to taskList.
     */
    private void afterChange() {
        Storage.save(taskList);
    }
}

