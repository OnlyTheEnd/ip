package veigar.task;

/**
 * ToDos are tasks with no specified date or time.
 */
public class ToDo extends Task {

    public ToDo(String description) {
        super(description, "veigar.task.ToDo");
    }

    /**
     * Formats the ToDo to display to user.
     * @return Formats the ToDo to display to user in the format [T][complete?]description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
