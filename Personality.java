import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Personality {
    
    

    // The main method to process the data from the personality tests
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in); 
        Scanner fileScanner = getFileScanner(keyboard);
        analyzeFile(fileScanner);
        

        fileScanner.close();
        keyboard.close();
    }

    
    
    
    // Method to choose a file.
    // Asks user for name of file. 
    // If file not found create a Scanner hooked up to a dummy set of data
    // Example use: 
    public static Scanner getFileScanner(Scanner keyboard){
        Scanner result = null;
        try {
            System.out.print("Enter the name of the file with the personality data: ");
            String fileName = keyboard.nextLine().trim();
            System.out.println();
            result = new Scanner(new File(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println("Problem creating Scanner: " + e);
            System.out.println("Creating Scanner hooked up to default data " + e);
            String defaultData = "1\nDEFAULT DATA\n"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
            result = new Scanner(defaultData);
        }
        return result;
    }
    
    // goes through the entire file and prints the name of each participant
    // and analyzes the data
    public static void analyzeFile (Scanner fileScanner) {
    	fileScanner.nextLine();
    	while (fileScanner.hasNextLine()) {
    		String name = fileScanner.nextLine();
    		System.out.printf("%28s:", name);
    		String answers = fileScanner.nextLine();
    		int [] traitValues = lineAnalysis(answers);
    		produceData(traitValues);
    		System.out.println();
    	}
    	
    }
    
    // creates a new array with a tally of the number of answers corresponding to each of the 8 traits
    public static int[] lineAnalysis (String line) {
    	line = line.toUpperCase();
    	char[] answers = line.toCharArray();
    	int [] traitValues = new int[8];
    	for (int i = 0; i < answers.length; i += 7) {
    		countValues(traitValues, answers[i], 0, 1);
    		countValues(traitValues, answers[i + 1], 2, 3);
    		countValues(traitValues, answers[i + 2], 2, 3);
    		countValues(traitValues, answers[i + 3], 4, 5);
    		countValues(traitValues, answers[i + 4], 4, 5);
    		countValues(traitValues, answers[i + 5], 6, 7);
    		countValues(traitValues, answers[i + 6], 6, 7);
    	}
    	return traitValues;
    }
    
    // adds one to the index of a specific trait (trait specified through the parameters)
    public static void countValues (int[] traitValues, char answer, int aIndex, int bIndex) {
    	if (answer == 'A') {
    		traitValues[aIndex]++;
    	}
    	else if (answer == 'B') {
    		traitValues[bIndex]++;
    	}
    }
    
    // prints out the percentages for each "B" personality trait, 
    // and gives the overall personality type
    public static void produceData (int [] traitValues) {
    	int introversion = calculatePercentage(traitValues[0], traitValues[1]);
    	int intuition = calculatePercentage(traitValues[2], traitValues[3]);
    	int feeling = calculatePercentage(traitValues[4], traitValues[5]);
    	int perceiving = calculatePercentage(traitValues[6], traitValues[7]);
    	checkForNoAnswers(introversion);
    	checkForNoAnswers(intuition);
    	checkForNoAnswers(feeling);
    	checkForNoAnswers(perceiving);
    	System.out.print(" = ");
    	personalityType(introversion, "E", "I");
    	personalityType(intuition, "S", "N");
    	personalityType(feeling, "T", "F");
    	personalityType(perceiving, "J", "P");
    }
    
    // calculates the percentage of a trait, 
    // and returns -1 if no questions were answer, 
    // returns the percentage otherwise
    public static int calculatePercentage (int aValue, int bValue) {
    	if (aValue + bValue == 0) {
    		return -1;
    	}
    	double decimal = 100.0 * bValue / (aValue + bValue);
    	int percent = (int) (decimal + 0.5);
    	return percent;
    }
    
    // determines the personality type based on the prevalence of certain traits
    // prints X if it's 50/50, - if no questions for that trait were answered
    public static void personalityType (int trait, String letterOne, String letterTwo) {
    	if (trait < 50 && trait != -1) {
    		System.out.print(letterOne);
    	}
    	else if (trait > 50) {
    		System.out.print(letterTwo);
    	}
    	else if (trait == -1) {
    		System.out.print("-");
    	}
    	else if (trait == 50) {
    		System.out.print("X");
    	}
    } 
    
    // prints "NO ANSWERS" in place of the percentage if no questions were answered
    public static void checkForNoAnswers (int traitPercent) {
    	if(traitPercent == -1) {
    		System.out.printf("%11s", "NO ANSWERS");
    	}
    	else {
    		System.out.printf("%11s", traitPercent);
    	}
    }
}  