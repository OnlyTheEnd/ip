import java.util.List;

public class Command {
    private final COMMAND command; // already exists (singleton)

    public Command(COMMAND command) {
        this.command = command;

    }
    enum COMMAND {

        BYE {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) {
                System.out.println("    I shall return, better and BIGGER!!!");
            }
        },

        LIST {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) {
                System.out.println("    Suffering awaits!");
                if (taskList.isEmpty()) {
                    System.out.println("    DON'T DEAD OPEN INSIDE LIST IS 0");
                    return;
                }
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println(((i + 1) + ". " + taskList.get(i)).indent(4));
                }
            }
        },

        MARK {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                int n = parseIndex(args, taskList);
                System.out.println("    Your commands tire me.");
                taskList.get(n).markDone();
                System.out.println(taskList.get(n).toString().indent(4));
            }
        },

        UNMARK {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                int n = parseIndex(args, taskList);
                System.out.println("    No, no, NO! THIS ISN'T OVER!!!");
                taskList.get(n).markUndone();
                System.out.println(taskList.get(n).toString().indent(4));
            }
        },

        TODO {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    throw new VeigarException("You have nothing");
                }
                taskList.add(new ToDo(args));
                afterAdd(taskList);
            }
        },

        EVENT {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                taskList.add(new Event(parts[0], parts[1], parts[2]));
                afterAdd(taskList);
            }
        },

        DEADLINE {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                taskList.add(new Deadline(parts[0], parts[1]));
                afterAdd(taskList);
            }
        },

        DELETE {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                int n = parseIndex(args, taskList);
                System.out.println("    I have deleted this task:\n    " + taskList.get(n));
                taskList.remove(n);
                afterAdd(taskList);
            }
        },
        SHOW {
            @Override
            void execute(Ui ui, List<Task> taskList, String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                boolean found = false;

                for (int i = 0; i < taskList.size(); i++) {
                    Task t = taskList.get(i);
                    String taskDate = null;

                    if (t instanceof Deadline d) {
                        taskDate = d.getBy().split(",")[0].trim();
                    } else if (t instanceof Event e) {
                        taskDate = e.getToDate().split(",")[0].trim();
                    } else {
                        continue; // skip other task types
                    }

                    if (taskDate.equals(queryDate)) {
                        System.out.println("    " + (i + 1) + ". " + t);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("    No tasks found on " + queryDate);
                }

            }

        }


        ;
        abstract void execute(Ui ui, List<Task> taskList, String args) throws VeigarException;

        static int parseIndex(String s, List<Task> taskList) throws VeigarException {
            try {
                int n = Integer.parseInt(s) - 1;
                if (n < 0 || n >= taskList.size()) {
                    throw new VeigarException("Your commands are wrong");
                }
                return n;
            } catch (NumberFormatException e) {
                throw new VeigarException("Please provide a valid number");
            }
        }

        static void afterAdd(List<Task> taskList) {
            System.out.println("    Now you have " + taskList.size() + " tasks in the list");
            Storage.save(taskList);
        }
    }

    public void execute(List<Task> tasks, Ui ui, Storage storage, String args) throws VeigarException {
        command.execute(ui, tasks, args);
    }





    public Boolean isActive() {
        return command != COMMAND.BYE;
    }
}

