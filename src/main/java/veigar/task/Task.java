package veigar.task;

import java.time.format.DateTimeFormatter;

/**
 * Tasks includes ToDo, Event and Deadline
 */
public abstract class Task {
    /**
     * Standardises date and time output as for example 27 Mar 2003, 2200.
     */
    public static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("d MMM uuuu, HHmm");

    protected String type;
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task that is not isDone.
     * @param description A task the user has to do.
     * @param type Type of Task
     */
    public Task(String description, String type) {
        assert description != null;
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void markDone() {
        this.isDone = true;
    }
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Formats the Task to be seen by the user.
     * @return The task as specified under each type,[X] for completed, [ ] for incomplete.
     */
    @Override
    public String toString() {
        if (this.isDone) {
            return "[X]" + this.description;
        } else {
            return "[ ]" + this.description;
        }

    }
}

