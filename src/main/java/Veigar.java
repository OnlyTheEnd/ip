import veigar.command.Command;

import java.util.Scanner;

import veigar.command.CommandResult;
import veigar.exception.VeigarException;
import veigar.tools.Storage;
import veigar.tools.TaskList;
import veigar.tools.Ui;


/**
 * Main class.
 */
public class Veigar {
    private Ui ui;
    private TaskList tasks;

    /**
     * Initialises Ui, sets the filePath and loads TaskList
     * @param filePath Destination path file to load the Task List
     */
    public Veigar(String filePath) {
        ui = new Ui();
        Storage.setPath(filePath);
        tasks = new TaskList(Storage.load());

    }

    /**
     * While not exiting, parses the users commands into commands and further arguments.
     * Executes the specified command if found, else warns the user about it.
     */
    public void run() {
        System.out.println("I am VeigarBot \nHEHEHEHA");

        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            try {
                String s = scanner.nextLine();
                String[] cmd = s.split(" ", 2);
                String commandArgs = cmd.length > 1 ? cmd[1] : "";
                Command c = new Command(Command.Cmd.valueOf(cmd[0].toUpperCase()));
                CommandResult cr = c.execute(ui, tasks, commandArgs);
                //for reply
                System.out.println(cr.getFeedbackToUser());
                isExit = cr.isExit();
            } catch (IllegalArgumentException | VeigarException e) {
                System.out.println("Whoops wrong command, Suffering awaits!");
            }
        }
    }


    public static void main(String[] args) {
        new Veigar("data/tasks.json").run();
    }


}


