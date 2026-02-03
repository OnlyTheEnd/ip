package veigar.command;

import veigar.task.Deadline;
import veigar.task.Event;
import veigar.task.ToDo;
import veigar.tools.Parser;
import veigar.tools.TaskList;
import veigar.exception.VeigarException;
import veigar.tools.Ui;

public class Command {
    private final COMMAND command; // already exists (singleton)

    public Command(COMMAND command) {
        this.command = command;

    }
    public enum COMMAND {

        BYE {
            @Override
            void execute(Ui ui, TaskList taskList, String args) {
                System.out.println("    I shall return, better and BIGGER!!!");
            }
        },

        LIST {
            @Override
            void execute(Ui ui, TaskList taskList, String args) {
                System.out.println("    Suffering awaits!");
                taskList.listTasks();
            }
        },

        MARK {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                System.out.println("    Your commands tire me.");
                taskList.getTask(n).markDone();
                System.out.println(taskList.getTask(n).toString().indent(4));
            }
        },

        UNMARK {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                System.out.println("    No, no, NO! THIS ISN'T OVER!!!");
                taskList.getTask(n).markUndone();
                System.out.println(taskList.getTask(n).toString().indent(4));
            }
        },

        TODO {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    throw new VeigarException("You have nothing");
                }
                taskList.addTask(new ToDo(args));
            }
        },

        EVENT {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                taskList.addTask(new Event(parts[0], parts[1], parts[2]));
            }
        },

        DEADLINE {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                taskList.addTask(new Deadline(parts[0], parts[1]));
            }
        },

        DELETE {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                System.out.println("    I have deleted this task:\n    " + taskList.getTask(n));
                taskList.removeTask(n);
            }
        },
        SHOW {
            @Override
            void execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                taskList.showTasks(queryDate);
            }

        };
        abstract void execute(Ui ui, TaskList taskList, String args) throws VeigarException;
    }

    public void execute(Ui ui, TaskList tasks, String args) throws VeigarException {
        command.execute(ui, tasks, args);
    }

    public Boolean isActive() {
        return command != COMMAND.BYE;
    }
}

