import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//Ignore the funky spacing
//I was learning how to "checkstyle"

public class Histogram {
    public static void main(String[] args) {
        String fileName = args[0];
	int nBins =  Integer.parseInt(args[1]);

	//101 to account for the 0. This information tells
	//me the DIFFERENCE betweent the bins
	//and only that. Not where it starts or etc.
	int binIncrement = 101 / nBins;
	int addToBottomBin = 101 % nBins;
	int[] scores = new int[nBins];


	//actual path: C:\Users\Alex\Documents\CS1331\hw1\grades.csvtr
	try {
	    Scanner scanner = new Scanner(new File(fileName));
	    //SCANS ONLY ONCE
	    while (scanner.hasNext()) {
		String temp = scanner.nextLine();
		//take the last parts of the string to get the numbers
		//and the ',' then rids space and comma
		int score = Integer.parseInt(temp.substring(temp.length() - 3,
					     temp.length()).replace(",", "")
					     .replace(" ", ""));
		//Basically adjusts for the bottom increment on the bottom
		//and divides by increment to find index
		int whichBin = (score - addToBottomBin) / binIncrement;

		//if score is 0, and you subtract by addToBottomBin,
		//it's still in the first bin even if neg
		if (whichBin < 0) {
		    whichBin = 0;
		}
		scores[whichBin]++;
	    }
	scanner.close();
	}
	catch (FileNotFoundException e) {
	    e.printStackTrace();
        }
	//placeholder variable for the text displaying a range of values
	int rangeValues = 100;

	//Loop printing everything
	for (int i = scores.length - 1; i > -1; i--) {

	    String ranges = "";
	    if (i == 0) {
		ranges += ("" + rangeValues + " - "
			   + (rangeValues
			   - (binIncrement + addToBottomBin - 1)));
		//I had to subtract 1, because if I included it
		//it would include the next bin
		rangeValues -= (binIncrement + addToBottomBin);
	    }
	    else {
		ranges += ("" + rangeValues + " - "
			  + (rangeValues - (binIncrement - 1)));
		rangeValues -= binIncrement;
	    }

	    //beautiful format
	    System.out.printf("%9s | ", ranges);

	    //second loop to print out each bracket or histogram bar.
	    for (int j = 0; j < scores[i]; j++) {
		System.out.print("[]");
	    }

	    System.out.println("");
	}
    }
}
