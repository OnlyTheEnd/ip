import java.util.Scanner;
public class Veigar {
    static boolean active = true;
    enum COMMAND {
        BYE,
        LIST,
        BLAH
    }
    public static void texts(COMMAND command){
        System.out.println(command.name().toLowerCase());
        switch (command) {
            case BYE:
                //exit
                System.out.println("I shall return, better and BIGGER!!!");
                active = false;
                break;
            case BLAH:

            case LIST:
                return;
        }

    }

    public static void main(String[] args){
        //introduction
        System.out.println("I am VeigarBot \nYour commands tire me.");

        Scanner scanner = new Scanner(System.in);
        while (active) {
            COMMAND c = COMMAND.valueOf(scanner.nextLine().toUpperCase());
            texts(c);

        }
    }
}


