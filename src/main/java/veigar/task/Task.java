package veigar.task;


public abstract class Task {

    protected String type;
    protected String description;
    protected boolean done;

    public Task(String description, String type){
        this.type = type;
        this.description = description;
        this.done = false;
    }

    public void markDone() {
        this.done = true;
    }
    public void markUndone() {
        this.done = false;
    }


    @Override
    public String toString() {
        if (this.done) {
            return "[X]" + this.description;
        } else {
            return "[ ]" + this.description;
        }

    }
}

