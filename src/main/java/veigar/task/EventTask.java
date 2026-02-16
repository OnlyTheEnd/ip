package veigar.task;
import java.time.LocalDateTime;

import veigar.exception.VeigarException;
import veigar.tools.Parser;



/**
 * Events are tasks that happens within a set time period.
 */
public class EventTask extends Task {
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    /**
     * Creates an event task with specified description, from date and time and to date and time.
     * @param description Event Task that is happening.
     * @param fromDate Event start date and time.
     * @param toDate Event end date and time.
     */
    public EventTask(String description, String fromDate, String toDate) throws VeigarException {
        super(description, "veigar.task.Event");
        assert fromDate != null;
        assert toDate != null;
        this.fromDate = Parser.parseDateTime(fromDate.trim());
        this.toDate = Parser.parseDateTime(toDate.trim());
        if (this.fromDate.isAfter(this.toDate)) {
            throw new VeigarException("Date order is inverted");
        }

    }
    public LocalDateTime getToDate() {
        return toDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    /**
     * Formats the Event to display to user.
     * @return Deadline details in the format [E][complete?]description (from: date and time to: date and time).
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "\n(from: " + this.fromDate.format(OUTPUT_FORMAT)
                + " to: " + this.toDate.format(OUTPUT_FORMAT) + ")";
    }
}
