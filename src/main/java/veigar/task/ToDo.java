package veigar.task;

public class ToDo extends Task{

    public ToDo(String description) {
        super(description, "veigar.task.ToDo");
    }


    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
