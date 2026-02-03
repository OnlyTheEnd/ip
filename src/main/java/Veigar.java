import veigar.command.Command;
import veigar.exception.VeigarException;
import veigar.tools.Storage;
import veigar.tools.TaskList;
import veigar.tools.Ui;

import java.util.Scanner;

public class Veigar {
    static boolean active = true;
    private Ui ui;
    private TaskList tasks;

    public Veigar(String filePath) {
        ui = new Ui();
        tasks = new TaskList(Storage.load());
        Storage.setPath(filePath);

    }

    public void run() {
        System.out.println("I am VeigarBot \nHEHEHEHA");

        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        while (isActive) {
            try {
            String s = scanner.nextLine();
            String[] cmd = s.split(" ", 2);
            String commandArgs = cmd.length > 1 ? cmd[1] : "";
            Command c = new Command(Command.COMMAND.valueOf(cmd[0].toUpperCase()));
            c.execute(ui, tasks, commandArgs);
            isActive = c.isActive();
            } catch (IllegalArgumentException | VeigarException e) {
                System.out.println("    Whoops wrong command, Suffering awaits!");
            }
        }
    }


    public static void main(String[] args) {
        //introduction
        new Veigar("data/tasks.json").run();
    }


}


