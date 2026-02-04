import veigar.command.Command;

import java.util.Scanner;

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
     * While active, parses the users commands into commands and further arguments.
     * Executes the specified command if found.
     */
    public void run() {
        System.out.println("I am VeigarBot \nHEHEHEHA");

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        while (isActive) {
            try {
                String s = scanner.nextLine();
                String[] cmd = s.split(" ", 2);
                String commandArgs = cmd.length > 1 ? cmd[1] : "";
                Command c = new Command(Command.Cmd.valueOf(cmd[0].toUpperCase()));
                c.execute(ui, tasks, commandArgs);
                isActive = c.isActive();
            } catch (IllegalArgumentException | VeigarException e) {
                System.out.println("    Whoops wrong command, Suffering awaits!");
            }
        }
    }


    public static void main(String[] args) {
        new Veigar("data/tasks.json").run();
    }


}


