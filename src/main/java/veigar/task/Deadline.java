package veigar.task;
import veigar.tools.Parser;

public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String byString) {
        super(description, "veigar.task.Deadline");
        this.by = Parser.parseDateTime(byString.trim());
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + this.by + ")";
    }
}
