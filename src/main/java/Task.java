public abstract class Task {
    protected String description;
    protected boolean done;

    public Task(String description){
        this.description = description;
        this.done = false;
    }

    public void markDone() {
        this.done = true;
    }
    public void markUndone() {
        this.done = false;
    }

    public abstract String toSave();

    public abstract String fromLoad();
    @Override
    public String toString() {
        if (this.done) {
            return "[X]" + this.description;
        } else {
            return "[ ]" + this.description;
        }



    }
}

