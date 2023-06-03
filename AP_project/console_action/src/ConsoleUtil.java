import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner scanner = new Scanner(System.in);
    /*
     scans the user's choice in string type
     */
    public static String waitForString() {
        while (true) {
            String val = scanner.nextLine();

            if (!val.isBlank()) {
                return val.trim();
            }
        }
    }
    /*
    if user type a string in non-integer type it throw an exception that it is invalid
     */
    public static int waitForInteger() {
        while (true) {
            String val = waitForString();

            try {
                return Integer.parseInt(val);
            } catch (Exception ignore) {
                System.err.println("Invalid!");
            }
        }
    }
    /*
    * waits for the user to enter the choice , if choice is out of range, Invalid command
     */
    public static int waitForCommand(int rangeFrom, int rangeTo) {
        while (true) {
            int cmd = waitForInteger();
            if (cmd < rangeFrom || cmd > rangeTo) {
                System.err.println("Invalid Command!");
            } else {
                return cmd;
            }
        }
    }

    public static void printCommandHint(String command) {
        printColored(36, command);
    }

    public static void printColored(int color, String text) {
        System.out.println("\u001B[" + color + "m" + text + "\u001B[0m");
    }

    public static void printHello(UserToBeSigned user) {
        printColored(33, "Hello " + user.getFirstName() + user.getLastName() );
        printColored(33, "Your Email: " + user.getEmail());
        printColored(33, "WELCOME TO CHAT!");
    }

    public static void printJoinMessage(UserToBeSigned user) {
        printColored(32, user.getUsername() + " Joined! (" + user.getFirstName() + user.getLastName()  + ")");
    }

    public static void printLeftMessage(UserToBeSigned user) {
        printColored(31, user.getUsername() + " Left! (" + user.getFirstName() + user.getLastName() + ")");
    }

//    public static void printMessage(MessageModel messageModel) {
//        printColored(34, messageModel.getSender().getUsername() +
//                ":\u001B[0m " + messageModel.getText());
//    }
}