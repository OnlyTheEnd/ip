package veigar.tools;

import java.util.List;

import veigar.task.Deadline;
import veigar.task.Event;
import veigar.task.Task;



/**
 * TaskList
 */
public class TaskList {
    private List<Task> taskList;

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int getListSize() {
        return taskList.size();
    }

    public Task getTask(int i) throws IndexOutOfBoundsException{
        try {
            return taskList.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("    Index out of bounds");
        }
        return taskList.get(0);
    }

    /**
     * Adds Task T into the taskList.
     * @param T A Task.
     */
    public void addTask(Task T) {
        taskList.add(T);
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

    /**
     * Displays the entire taskList as a String, each line representing one Task with a space after each one.
     * Displays empty message if size of taskList is 0.
     */
    public void listTasks() {
        int size = taskList.size();
        if (size == 0) {
            System.out.println("    DON'T DEAD OPEN INSIDE LIST IS 0");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(((i + 1) + ". " + taskList.get(i)).indent(4));
        }
    }

    /**
     * Displays the specified tasks which matches the queryDate date component.
     * @param queryDate Date of Task to be matched.
     */
    public void showTasks(String queryDate) {
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
                System.out.println("    " + (i + 1) + ". " + t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("    No tasks found on " + queryDate);
        }
    }
    /**
     * Displays the specified tasks which description matches the queryString.
     * @param queryString String of Task Description to be matched.
     */
    public void findTasks(String queryString) {
        boolean found = false;
        for (int i = 0; i < taskList.size(); i++) {
            String taskDescription = taskList.get(i).getDescription();
            if (taskDescription.contains(queryString)) {
                System.out.println("    " + (i + 1) + ". " + taskList.get(i));
                found = true;
            }

        }
        if (!found) {
            System.out.println("    No tasks found matching " + queryString);
        }
    }

    /**
     * Displays any updates to size of taskList when it changes
     */
    private void afterChange() {
        System.out.println("    Now you have " + taskList.size() + " tasks in the list");
        Storage.save(taskList);
    }
}

