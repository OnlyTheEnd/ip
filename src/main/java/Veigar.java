import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Veigar {
    static List<Task> storage = new ArrayList<Task>();
    static boolean active = true;
    enum COMMAND {
        BYE,
        LIST,
        BLAH,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    public static void texts(COMMAND command, String whole) throws VeigarException {
        //System.out.println(command.name().toLowerCase());

        switch (command) {
            case BYE:
                //exit
                System.out.println("    I shall return, better and BIGGER!!!");
                active = false;
                break;
            case LIST: {
                System.out.println("    Suffering awaits!");
                if (storage.isEmpty()) {
                    System.out.println("    DON'T DEAD OPEN INSIDE LIST IS 0");
                    break;
                }
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println(((i + 1) + ". " + storage.get(i).toString()).indent(4));
                }
                break;
            }
            case MARK: {
                int n = Integer.parseInt(whole);
                if (n > storage.size() + 1) {
                    throw new VeigarException("Your commands are wrong");
                }
                System.out.println("    Your commands tire me.");
                storage.get(n - 1).markDone();
                System.out.println(storage.get(n - 1).toString().indent(4));
                break;
            }

            case UNMARK: {
                int n = Integer.parseInt(whole);
                if (n > storage.size() + 1) {
                    throw new VeigarException("Your commands are wrong");
                }
                System.out.println("    No, no, NO! THIS ISN'T OVER!!!");
                storage.get(n - 1).markUndone();
                System.out.println(storage.get(n - 1).toString().indent(4));
                break;
            }

            case TODO: {
                if (whole.isEmpty()) {
                    throw new VeigarException("    You have nothing");
                }
                storage.add(new ToDo(whole));
                break;
            }

            case EVENT: {

                String[] parts = whole.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Event(parts[0], parts[1], parts[2]));
                break;
            }
            case DEADLINE: {
                String[] parts = whole.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Deadline(parts[0], parts[1]));
                break;
            }
            case DELETE: {
                int n = Integer.parseInt(whole);
                if (n > storage.size() + 1) {
                    throw new VeigarException("Your commands are wrong");
                }
                System.out.println("    I have deleted this task:\n    " + storage.get(n-1));
                storage.remove(n-1);

            }
        }
        if (command == COMMAND.TODO || command == COMMAND.EVENT
                || command == COMMAND.DEADLINE || command == COMMAND.DELETE) {
            System.out.println("    Now you have " + storage.size() + " tasks in the list");
        }
    }


    public static boolean isCommand(String value) {
        if (value == null) {
            return false;
        }
        try {
            COMMAND.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void main(String[] args){
            //introduction
            System.out.println("I am VeigarBot \nHEHEHEHA");

            Scanner scanner = new Scanner(System.in);
            try {
                while (active) {
                    String s = scanner.nextLine();
                    String[] ssplit = s.split(" ", 2);
                    String commandArgs = ssplit.length > 1 ? ssplit[1] : "";
                    if (isCommand(ssplit[0].toUpperCase())) {
                        COMMAND c = COMMAND.valueOf(ssplit[0].toUpperCase());
                        texts(c, commandArgs);
                    } else {
                        System.out.println("    Whoops no command,Suffering awaits!");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("    ERROR! ERROR! ERROR!");
            } catch (VeigarException e) {
                System.out.println("    " + e.getMessage());
            }

    }


}


