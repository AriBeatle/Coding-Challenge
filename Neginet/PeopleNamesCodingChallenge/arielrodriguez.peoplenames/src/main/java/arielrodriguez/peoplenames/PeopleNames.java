/**
 * 
 */
package main.java.arielrodriguez.peoplenames;

import java.io.File;

import main.java.arielrodriguez.peoplenames.fileprocessor.FileProcessor;
import main.java.arielrodriguez.peoplenames.fileprocessor.FileProcessor.NameType;

/**
 * @author Ariel Rodriguez
 *
 */
public class PeopleNames {
	
	private static String fileName;
	private static int nbrOfModifNames;
	
	public static boolean validateArguments(String[] args) {
		
		// Check the amount of parameters
		if (args.length != 2) {
			System.out.println("Invalid number of arguments. The following arguments are required: ");
			System.out.println("- fileName: Path and name of the input file\n" 
					           + "- nbrOfModifNames: Number of Modified full names to print");
			return false;
		}
		
		try {
			// Validate the file is valid.
			fileName = args[0];
			File f = new File(fileName);
			
			if (!f.exists() || !f.isFile()) {
				System.out.println("Invalid file name: " + args[0]);
				return false;
			}
		} catch (Exception e) {
			System.out.println("Invalid file name: " + args[0]);
			e.printStackTrace();
			return false;
		}
		
		nbrOfModifNames = Integer.parseInt(args[1]);		
		
		return true;
	}

	/**
	 * @param args
	 * fileName: Name of the input file
	 * nbrOfModifNames: Number of Modified full names to print
	 * 
	 */
	public static void main(String[] args) {		
		try {		
			
			//Process and validate command line arguments
			if(validateArguments(args)) {
			
				//Read input file & set the number of modified names to print
				FileProcessor fp = new FileProcessor(fileName, nbrOfModifNames);
				
				//Print the cardinality
				System.out.println("1. The names cardinality for full, last and first names:");
				fp.printCardinality();
				
				//Print the ten most common last names ordered 
				System.out.println("\n2. The most common last names are:");
				fp.printMostCommonNames(NameType.LASTNAME);
				
				//Print the ten most common first names
				System.out.println("\n3. The most common first names are:");
				fp.printMostCommonNames(NameType.FIRSTNAME);
				
				//Print modified names
				System.out.println("\n4. List of Modified Names:");
				fp.printModifiedNames();
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}