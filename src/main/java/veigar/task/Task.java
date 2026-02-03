package veigar.task;

/**
 * Tasks includes ToDo, Event and Deadline
 */
public abstract class Task {

    protected String type;
    protected String description;
    protected boolean done;

    /**
     * Creates a task that is not done.
     * @param description A task the user has to do.
     * @param type Type of Task
     */
    public Task(String description, String type){
        this.type = type;
        this.description = description;
        this.done = false;
    }

    public String getDescription() {
        return description;
    }

    public void markDone() {
        this.done = true;
    }
    public void markUndone() {
        this.done = false;
    }

    /**
     * Formats the Task to be seen by the user.
     * @return The task as specified under each type,[X] for completed, [ ] for incomplete.
     */
    @Override
    public String toString() {
        if (this.done) {
            return "[X]" + this.description;
        } else {
            return "[ ]" + this.description;
        }

    }
}

