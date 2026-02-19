package veigar.command;
import veigar.exception.VeigarException;
import veigar.task.DeadlineTask;
import veigar.task.EventTask;
import veigar.task.ToDoTask;
import veigar.tools.Parser;
import veigar.tools.TaskList;



/**
 * Represents Commands by the user to be executed.
 */
public class Command {
    /**
     * enums for all the possible commands.
     */
    public enum Cmd {

        BYE {
            @Override
            public CommandResult execute(TaskList taskList, String args) {
                String exitString = "I shall return, better and BIGGER!!!";
                return new CommandResult(exitString, false, true);
            }
        },

        LIST {
            @Override
            public CommandResult execute(TaskList taskList, String args) {
                String listString = "Suffering awaits!\n" + taskList.listTasks();
                return new CommandResult(listString);
            }
        },

        MARK {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                taskList.markDone(n);
                String markString = "Your commands tire me.\n" + taskList.getTask(n).toString();
                return new CommandResult(markString);
            }
        },

        UNMARK {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                taskList.markUndone(n);
                String unmarkString = "No, no, NO! THIS ISN'T OVER!!!\n" + taskList.getTask(n).toString();
                return new CommandResult(unmarkString);
            }
        },

        TODO {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    throw new VeigarException("You have nothing in your args");
                }
                taskList.addTask(new ToDoTask(args));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        EVENT {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("Invalid number of args, use /from date /to date");
                }
                taskList.addTask(new EventTask(parts[0], parts[1], parts[2]));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        DEADLINE {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("Invalid number of args, use /by date");
                }
                taskList.addTask(new DeadlineTask(parts[0], parts[1]));
                return new CommandResult("Now you have " + taskList.getListSize() + " tasks in the list");
            }
        },

        DELETE {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                int n = Parser.parseIndex(args, taskList);
                String deleteString = "I have deleted this task:\n" + taskList.getTask(n);
                taskList.removeTask(n);
                return new CommandResult(deleteString);
            }
        },
        SHOW {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                String showString = taskList.matchTasks(queryDate);
                return new CommandResult(showString);
            }
        },
        FIND {
            @Override
            public CommandResult execute(TaskList taskList, String args) throws VeigarException {
                String queryString = args.trim();
                String findString = taskList.findTasks(queryString);
                return new CommandResult(findString);
            }
        };

        public abstract CommandResult execute(TaskList taskList, String args) throws VeigarException;
    }
}

