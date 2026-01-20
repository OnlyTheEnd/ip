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
        UNMARK
    }

    public static void texts(COMMAND command, String[] whole){
        //System.out.println(command.name().toLowerCase());
        switch (command) {
            case BYE:
                //exit
                System.out.println("I shall return, better and BIGGER!!!");
                active = false;
                break;
            case BLAH:

            case LIST: {
                System.out.println("Suffering awaits!");
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println((i + 1) + ". " + storage.get(i).toString());
                }
                break;
            }
            case MARK: {
                int n = Integer.parseInt(whole[1]);
                System.out.println("Your commands tire me.\n");
                storage.get(n-1).markDone();
                System.out.println(storage.get(n).toString());
                break;
            }

            case UNMARK: {
                int n = Integer.parseInt(whole[1]);
                System.out.println("No, no, NO! THIS ISN'T OVER!!!");
                storage.get(n-1).markUndone();
                System.out.println(storage.get(n).toString());
                break;
            }
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
        while (active) {
            String s = scanner.nextLine();
            String[] ssplit = s.split(" ");
            if (isCommand(ssplit[0].toUpperCase())) {
                COMMAND c = COMMAND.valueOf(ssplit[0].toUpperCase());
                texts(c, ssplit);
            }
            else {
                storage.add(new Task(s));
                System.out.println("added: " + s);
            }
        }
    }

}


