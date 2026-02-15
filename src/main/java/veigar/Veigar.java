package veigar;

import veigar.command.Command;
import veigar.command.CommandResult;
import veigar.exception.VeigarException;
import veigar.tools.Storage;
import veigar.tools.TaskList;


/**
 * veigar.Main class.
 */
public class Veigar {
    private TaskList tasks;

    /**
     * Initialises Ui, sets the filePath and loads TaskList
     * @param filePath Destination path file to load the Task List
     */
    public Veigar(String filePath) {
        Storage.setPath(filePath);
        tasks = new TaskList(Storage.load());

    }

    /**
     * Parses the users commands into commands and further arguments.
     * Executes the specified command if found, else warns the user about it.
     */
    public CommandResult getResponse(String input) {
        try {
            String[] cmd = input.split(" ", 2);
            String commandArgs = cmd.length > 1 ? cmd[1] : "";
            //for reply
            return Command.Cmd.valueOf(cmd[0].toUpperCase()).execute(tasks, commandArgs);
        } catch (IllegalArgumentException e) {
            return new CommandResult("Error! Error! Your commands are wrong");
        } catch (VeigarException v) {
            return new CommandResult("Error! Error!" + v.getMessage());
        }
    }

    public static void main(String[] args) {
        new Veigar("data/tasks.json");
    }


}


