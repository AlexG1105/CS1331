import java.util.Random;

/**
 *@author aguo36
 *@version 1.0
 */

public class VendingMachine {
    private static double totalSales;
    private VendingItem[][][] shelf;
    private int luckyChance;
    private Random rand;

    /**Constructor
     *@param
     *@return
     */

    public VendingMachine() {
        totalSales = 0;
        shelf = new VendingItem[6][3][5];
        luckyChance = 0;
        rand = new Random();
        restock();
    }
    /** Dispense an item from the vending machine
     *@param code representation of user input
     *@return VendingItem: return the object at the specified location
     */
    public VendingItem vend(String code) {
        boolean validInput = true;
        String numberRegex = ".*[1-3].*";
        String letterRegex = ".*[A-F].*";
        if (code.length() != 2
            || !(code.matches(numberRegex)
                 && code.matches(letterRegex))) {
            validInput = false;
        } else {
            if (!(Character.toString(code.charAt(0)).matches(letterRegex)
                  && Character.toString(code.charAt(1)).matches(numberRegex))) {
                validInput = false;
            }
        }

        if (validInput) {
            int currentRow = (int) (code.charAt(0)) - 65;
            int currentCol = Character.getNumericValue(code.charAt(1)) - 1;
            if (shelf[currentRow][currentCol][0] == null) {
                System.out.println("ERROR: No More Items Left.");
                return null;
            } else {
                VendingItem temp = shelf[currentRow][currentCol][0];
                for (int i = 0; i < 5; i++) {
                    if (i == 4) {
                        shelf[currentRow][currentCol][i] = null;
                    } else {
                        shelf[currentRow][currentCol][i] =
                            shelf[currentRow][currentCol][i + 1];
                    }
                }
                if (free()) {
                    System.out.println("Congrats! You Got A Free Item!");
                    return temp;
                } else {
                    totalSales += temp.getPrice();
                    return temp;
                }
            }
        } else {
            System.out.println("ERROR: Invalid Input.");
            return null;
        }
    }
    /** Randomly determining whether an item should be free
     *@param
     *@return boolean: determining based on chance a free item
     */
    public boolean free() {
        if (rand.nextInt(100) < luckyChance) {
            luckyChance = 0;
            return true;
        } else {
            luckyChance++;
            return false;
        }
    }
    /** Fills all of the machine with random items
     *@param
     */
    public void restock() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 5; k++) {
                    VendingItem[] item = VendingItem.values();
                    int randIndex = rand.nextInt(item.length);
                    shelf[i][j][k] = item[randIndex];
                }
            }
        }
    }
    /** Get method for total sales made
     *@param
     *@return double: total sales
     */
    public static double getTotalSales() {
        return totalSales;
    }
    /** Get method for total items in machine
     *@param
     *@return int: total number of items in machine
     */
    public int getNumberOfItems() {
        int numberItems = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 5; k++) {
                    if (shelf[i][j][k] != null) {
                        numberItems++;
                    }
                }
            }
        }
        return numberItems;
    }
    /** Get method for total value of items in vending mach.
     *@param
     *@return double: total value
     */
    public double getTotalValue() {
        int totalValue = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 5; k++) {
                    if (shelf[i][j][k] != null) {
                        totalValue += shelf[i][j][k].getPrice();
                    }
                }
            }
        }
        return totalValue;
    }
    /** Get method for lucky chance
     *@param
     *@return int: luckyChance
     */
    public int getLuckyChance() {
        return luckyChance; //testing purposes
    }
    /** Regular toString method to represent vending machine
     *@param
     *@return String: VendingMachine
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("----------------------------------------------------------"
                 + "------------\n");
        s.append("                            VendaTron 9000                "
                 + "            \n");
        for (int i = 0; i < shelf.length; i++) {
            s.append("------------------------------------------------------"
                     + "----------------\n");
            for (int j = 0; j < shelf[0].length; j++) {
                VendingItem item = shelf[i][j][0];
                String str = String.format("| %-20s ",
                             (item == null ? "(empty)" : item.name()));
                s.append(str);
            }
            s.append("|\n");
        }
        s.append("----------------------------------------------------------"
                 + "------------\n");
        s.append(String.format("There are %d items with a total "
                 + "value of $%.2f.%n", getNumberOfItems(), getTotalValue()));
        s.append(String.format("Total sales across vending machines "
                               + "is now: $%.2f.%n", getTotalSales()));
        return s.toString();
    }
}
