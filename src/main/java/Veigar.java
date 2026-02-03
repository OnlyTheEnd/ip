import java.util.Scanner;

public class Veigar {
    static boolean active = true;
    private Ui ui;
    private TaskList tasks;
    private Storage storage;


    public Veigar(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());


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
            c.execute(tasks, ui, storage, commandArgs);
            isActive = c.isActive();
            } catch (IllegalArgumentException | VeigarException e) {
                System.out.println("    Whoops wrong command, Suffering awaits!");
            }
        }
    }


    public static void main(String[] args) {
        //introduction
        new Veigar("data/tasks.txt").run();
    }


}


