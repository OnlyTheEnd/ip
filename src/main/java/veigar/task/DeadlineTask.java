package veigar.task;
import java.time.LocalDateTime;

import veigar.exception.VeigarException;
import veigar.tools.Parser;



/**
 * Deadlines are tasks that need to be completed before by date.
 */
public class DeadlineTask extends Task {
    protected LocalDateTime byDate;

    /**
     * Creates a deadline task with specified description and end by date and time.
     * @param description Deadline task to complete.
     * @param byString Date and time of the deadline.
     */
    public DeadlineTask(String description, String byString) throws VeigarException {
        super(description, "veigar.task.Deadline");
        assert byDate != null;
        this.byDate = Parser.parseDateTime(byString.trim());

    }

    public LocalDateTime getBy() {
        return byDate;
    }

    /**
     * Formats the deadline to display to user.
     * @return Deadline details in the format [D][complete?]description (by: date and time).
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + "\n(by:" + this.byDate.format(OUTPUT_FORMAT) + ")";
    }
}
