package veigar.tools;

import veigar.task.Deadline;
import veigar.task.Event;
import veigar.task.Task;

import java.util.List;

public class TaskList {
    private List<Task> taskList;

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
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

    public void addTask(Task T) {
        taskList.add(T);
        afterChange(taskList);
    }

    public void removeTask(int i) {
        taskList.remove(i);
    }

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

    static void afterChange(List<Task> taskList) {
        System.out.println("    Now you have " + taskList.size() + " tasks in the list");
        Storage.save(taskList);
    }
}

