package veigar.command;
import veigar.exception.VeigarException;
import veigar.task.Deadline;
import veigar.task.Event;
import veigar.task.ToDo;
import veigar.tools.Parser;
import veigar.tools.TaskList;
import veigar.tools.Ui;


/**
 * Represents Commands by the user to be executed.
 */
public class Command {
    private final Cmd command;
    private final StringBuilder stringBuilder = new StringBuilder();
    public Command(Cmd command) {
        this.command = command;
    }

    /**
     * enums for all the possible commands.
     */
    public enum Cmd {

        BYE {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) {
                String exitString = "I shall return, better and BIGGER!!!";
                return new CommandResult(exitString, false, true);
            }
        },

        LIST {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) {
                String listString = "Suffering awaits!\n" + taskList.listTasks();
                return new CommandResult(listString);
            }
        },

        MARK {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                taskList.markDone(n);
                String markString = "Your commands tire me.\n" + taskList.getTask(n).toString();
                return new CommandResult(markString);
            }
        },

        UNMARK {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                taskList.markUndone(n);
                String unmarkString = "No, no, NO! THIS ISN'T OVER!!!\n" + taskList.getTask(n).toString();
                return new CommandResult(unmarkString);
            }
        },

        TODO {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    return new CommandResult("You have nothing in your args");
                }
                taskList.addTask(new ToDo(args));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        EVENT {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    return new CommandResult("Invalid number of args");
                }
                taskList.addTask(new Event(parts[0], parts[1], parts[2]));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        DEADLINE {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    return new CommandResult("Invalid number of args");
                }
                taskList.addTask(new Deadline(parts[0], parts[1]));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        DELETE {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                String deleteString = "I have deleted this task:\n" + taskList.getTask(n);
                taskList.removeTask(n);
                return new CommandResult(deleteString);
            }
        },
        SHOW {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                String showString = taskList.showTasks(queryDate);
                return new CommandResult(showString);
            }
        },
        FIND {
            @Override
            CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException {
                String queryString = args.trim();
                String findString = taskList.findTasks(queryString);
                return new CommandResult(findString);
            }
        };
        abstract CommandResult execute(Ui ui, TaskList taskList, String args) throws VeigarException;
    }

    /**
     * Executes the command.
     * @param ui Ui component.
     * @param tasks TaskList.
     * @param args args for enums.
     * @throws VeigarException on error.
     */
    public CommandResult execute(Ui ui, TaskList tasks, String args) throws VeigarException {
        return command.execute(ui, tasks, args);
    }

}

