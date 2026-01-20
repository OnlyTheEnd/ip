import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Veigar {
    static List<String> storage = new ArrayList<String>();
    static boolean active = true;
    enum COMMAND {
        BYE,
        LIST,
        BLAH
    }

    public static void texts(COMMAND command){
        //System.out.println(command.name().toLowerCase());
        switch (command) {
            case BYE:
                //exit
                System.out.println("I shall return, better and BIGGER!!!");
                active = false;
                break;
            case BLAH:

            case LIST:
                for (int i = 0; i < storage.size(); i ++) {
                    System.out.println((i+ 1) + ". " + storage.get(i));
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
        System.out.println("I am VeigarBot \nYour commands tire me.");

        Scanner scanner = new Scanner(System.in);
        while (active) {
            String s = scanner.nextLine();
            if (isCommand(s.toUpperCase())) {
                COMMAND c = COMMAND.valueOf(s.toUpperCase());
                texts(c);
            }
            else {
                storage.add(s);
                System.out.println("added: " + s);
            }
        }
    }

}


