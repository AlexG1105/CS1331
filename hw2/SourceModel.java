import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Create the class
 *@author Alex Guo
 *@version 1.0
 */
public class SourceModel {
    //Initialize variables so they can be accessed everywhere
    private String modelName;
    private int[][] characterCount;
    private double[] rowCount;
    private double[][] probability;
    /**
      * Create the class
      *@param name takes the name of the corpus
      *@param fileName takes the fileName of corpus
      */
    public SourceModel(String name, String fileName) {
        modelName = name;
        characterCount = new int[26][26];
        rowCount = new double[26];
        probability = new double[26][26];
        System.out.print("Training " + name + " model ...");

        try {
            Scanner scanner = new Scanner(new File(fileName));
            String temp = "";
            //append all of the text
            while (scanner.hasNext()) {
                temp += scanner.next();
            }
            //only keeps the letters and makes the lowercase
            temp = temp.replaceAll("[^A-Za-z]+", "").toLowerCase();
            //iterates through each letter then puts the letters
            //sequence to the respective row and column
            for (int i = 0; i < (temp.length() - 1); i++) {
                char firstLetter = temp.charAt(i);
                char secondLetter = temp.charAt(i + 1);
                //index based on ASCII values
                characterCount[(int) firstLetter - 97]
                    [(int) secondLetter - 97]++;
                rowCount[(int) firstLetter - 97]++;
            }
            //calculates the probability by dividing the count
            //by the total counts in each row
            for (int i = 0; i < probability.length; i++) {
                for (int j = 0; j < probability[i].length; j++) {
                    if (rowCount[i] == 0) {
                        rowCount[i] = 0.01;
                    }
                    probability[i][j] = ((double) characterCount[i][j])
                        / rowCount[i];
                    if (probability[i][j] == 0) {
                        probability[i][j] = 0.01;
                    }
                }
            }
            System.out.println("Done.");
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     *@return a string which contains the name
     */
    public String getName() {
        return modelName;
    }
    /**
     *@return a string witht he matrix
     */
    public String toString() {
        String matrixString = "";
        matrixString += " ";
        for (int i = 97; i < 123; i++) {
            matrixString += "    ";
            matrixString += (char) i;
        }
        matrixString += ("\n");
        for (int i = 0; i < probability.length; i++) {
            matrixString += ((char) (i + 97) + " ");
            for (int j = 0; j < probability[i].length; j++) {
                matrixString += String.format("%.2f", probability[i][j]);
                matrixString += (" ");
            }
            matrixString += "\n";
        }
        return matrixString;
    }
    /**
     *@param test a set of letters to test
     *@return the probability for the word
     */
    public double probability(String test) {
        test = test.replaceAll("[^A-Za-z]+", "").toLowerCase();
        double stringProbability = 1.0;
        for (int i = 0; i < test.length() - 1; i++) {
            int firstIndex = (int) (test.charAt(i)) - 97;
            int secondIndex = (int) (test.charAt(i + 1)) - 97;
            stringProbability *= probability[firstIndex][secondIndex];
        }
        return stringProbability;
    }
    /**
     *@param args the command line arguments
     */
    public static void main(String[] args) {
        SourceModel[] models = new SourceModel[args.length - 1];
        for (int i = 0; i < args.length - 1; i++) {
            models[i] = new SourceModel(args[i]
                                        .substring(0, args[i]
                                        .indexOf(".")), args[i]);
        }
        System.out.println("Analyzing: " + args[args.length - 1]);

        double[] normalizedProbability = new double[args.length - 1];
        double sumProbability = 0;
        for (int i = 0; i < args.length - 1; i++) {
            sumProbability += models[i].probability(args[args.length - 1]);
        }
        //normalize the probability in respect to the values given
        for (int i = 0; i < normalizedProbability.length; i++) {
            normalizedProbability[i] = models[i]
                .probability(args[args.length - 1]) / sumProbability;
        }

        int highestIndex = 0;
        for (int i = 0; i < args.length - 1; i++) {
            System.out.print("Probability that test string is");
            System.out.printf("%9s: ", models[i].getName());
            System.out.printf("%.2f", normalizedProbability[i]);
            System.out.println("");
            if (normalizedProbability[i]
                > normalizedProbability[highestIndex]) {
                highestIndex = i;
            }
        }
        System.out.println("Test string is most likely "
                           + models[highestIndex].getName() + ".");
    }
}
