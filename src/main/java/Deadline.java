public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String byString) {
        super(description, "Deadline");
        this.by = parseDateTime(byString.trim());
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + this.by + ")";
    }
}
