package veigar.task;
import veigar.tools.Parser;

/**
 * Deadlines are tasks that need to be completed before by date.
 */
public class Deadline extends Task{
    protected String by;

    /**
     * Creates a deadline task with specified description and end by date and time.
     * @param description Deadline task to complete.
     * @param byString Date and time of the deadline.
     */
    public Deadline(String description, String byString) {
        super(description, "veigar.task.Deadline");
        this.by = Parser.parseDateTime(byString.trim());
    }

    public String getBy() {
        return by;
    }

    /**
     * Formats the deadline to display to user.
     * @return Deadline details in the format [D][complete?]description (by: date and time).
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + this.by + ")";
    }
}
