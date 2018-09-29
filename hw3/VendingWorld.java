import java.util.Scanner;

/**
 * Client code for for VendingMachine classes.
 * @version 1.0
 * @author ftejani6
 */
public class VendingWorld {
    /**
     * Runner
     * @param args Program arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder clear = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            clear.append("\n");
        }
        VendingMachine culc = new VendingMachine();
        VendingMachine coc = new VendingMachine();
        VendingMachine current = null;
        System.out.println(clear);
        do {
            System.out.println("Which machine would you like to visit?");
            System.out.println("(1) CULC");
            System.out.println("(2) CoC");
            System.out.println("(3) Quit");

            String choice = sc.nextLine();

            if (choice.equals("1")) {
                current = culc;
            } else if (choice.equals("2")) {
                current = coc;
            } else if (choice.equals("3")) {
                System.exit(0);
            } else {
                System.out.println(clear);
                System.out.println("That is not a valid option. Try again.");
                continue;
            }
            System.out.println(clear);

            String item;
            do {
                System.out.println(current);
                System.out.println("Enter your choice (or type 'back' to go"
                    + " back to the main menu or"
                    + " 'restock' to restock the machine):");
                item = sc.nextLine().trim();
                if (item.equalsIgnoreCase("restock")) {
                    System.out.println(clear);
                    current.restock();
                    System.out.println("You have restocked the machine.");
                } else if (!item.equalsIgnoreCase("back")) {
                    System.out.println(clear);
                    System.out.println("You dispensed " + current.vend(item));
                } else {
                    System.out.println(clear);
                }
            } while (!item.equalsIgnoreCase("back"));
        } while (true);
    }
}
