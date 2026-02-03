package veigar.task;
import veigar.tools.Parser;
public class Event extends Task {
    protected String fromDate;
    protected String toDate;

    public Event(String description, String fromDate, String toDate) {
        super(description, "veigar.task.Event");
        this.fromDate = Parser.parseDateTime(fromDate.trim());
        this.toDate = Parser.parseDateTime(toDate.trim());
    }
    public String getToDate() {
        return toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.fromDate + " to: " + this.toDate + ")";
    }
}
