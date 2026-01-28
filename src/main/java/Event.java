public class Event extends Task {
    protected String fromDate;
    protected String toDate;
    public Event(String description, String fromDate, String toDate) {
        super(description, "Event");
        this.fromDate = parseDateTime(fromDate.trim());
        this.toDate = parseDateTime(toDate.trim());
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
