/**
 * 
 */
package main.java.arielrodriguez.peoplenames.fileprocessor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Ariel Rodriguez
 *
 */
public class FileProcessor {

    public enum NameType {
	FIRSTNAME, LASTNAME;
    }

    private Map<String, Integer> fullNames = new HashMap<String, Integer>();
    private Map<String, Integer> firstNames = new HashMap<String, Integer>();
    private Map<String, Integer> lastNames = new HashMap<String, Integer>();
    private int nbrOfOrigNames;
    private List<String> origNames = new ArrayList<String>();

    /*
     * loadNamesMap: Load the Names in the maps
     */
    private void loadNamesMap(Map<String, Integer> hm, String name) {

	// If it's the first occurrence put it with value 0.
	if (!hm.containsKey(name))
	    hm.put(name, 0);

	// Increment the counter
	hm.put(name, hm.get(name) + 1);
    }

    /*
     * Loads the origNames list with the names that follow these restrictions: - No
     * previous name has the same first name. - No previous name has the same last
     * name.
     */
    private void loadUniqueNamesList(String fullName, String lastName, String firstName) {

	/*
	 * If this is the first name read from the file, we need to add it to the list
	 * and return since it will comply with the restrictions.
	 */
	if (fullNames.size() == 1) {
	    origNames.add(fullName);
	    return;
	}

	/*
	 * Now only add the fullName to the list after checking that the last name and
	 * first name do not have more than one occurrence read from the file.
	 */
	if (firstNames.get(firstName) == 1 && lastNames.get(lastName) == 1)
	    origNames.add(fullName);
    }

    /*
     * loadNames: Defines the consumer function to be used in a Stream to load the
     * names read from the lines in the input file.
     */
    private Consumer<String> loadNames = new Consumer<String>() {

	@Override
	public void accept(String line) {

	    /*
	     * Get the Full, First and Last names from the line read.
	     */
	    String fullname = line.split(" -- ")[0];
	    String[] names = fullname.split(", ");

	    // Load the names to each corresponding map
	    loadNamesMap(fullNames, fullname);
	    loadNamesMap(lastNames, names[0]);
	    loadNamesMap(firstNames, names[1]);

	    /*
	     * Load the list of original names if the size is not yet the requested one.
	     */
	    if (origNames.size() < nbrOfOrigNames)
		loadUniqueNamesList(fullname, names[0], names[1]);

	}
    };

    /*
     * FileProcessor: This constructor takes as parameter the name of the input file
     * and reads the names into HashMaps which will be used to produce the outputs.
     * It also takes as parameter the number of original names to build up the list
     * for printing the modified names.
     */
    public FileProcessor(String fileName, int nbrOfOrigNames) throws Exception {

	this.nbrOfOrigNames = nbrOfOrigNames;

	/*
	 * Since the size of the file may be big, I'll traverse it using a Stream that
	 * will be populated lazily as it's being consumed.
	 */
	try (Stream<String> lines = Files.lines(Paths.get(fileName))) {

	    /*
	     * Read only the lines of the file that contain the name which follow this
	     * convention: "LastName, FirstName -- Text" and populate the HashMap with the
	     * Full Names. Names are only valid if they contain alphabetic characters.
	     */
	    lines.filter(line -> line.matches("[a-zA-Z]+, [a-zA-Z]+ -- .*")).forEach(loadNames);
	    lines.close();

	} catch (Exception e) {
	    throw e;
	}
    }

    /*
     * printCardinality: The cardinality is just the size of each map
     */
    public void printCardinality() {
	System.out.println("\tFull names : " + fullNames.size());
	System.out.println("\tLast names : " + lastNames.size());
	System.out.println("\tFirst names : " + firstNames.size());
    }

    /*
     * getMostCommonNames: Gets the 10 most common names, sorted in descending
     * order.
     */
    private void getMostCommonNames(Map<String, Integer> names) {

	/*
	 * Use an array to store the most Common Names and their occurrences.
	 */
	List<Map.Entry<String, Integer>> mostCommonNames = new ArrayList<Map.Entry<String, Integer>>();
	int index = 0;

	for (Map.Entry<String, Integer> currentEntry : names.entrySet()) {
	    if (index < 10) {
		// Load the 10 first entries as Max
		mostCommonNames.add(index, currentEntry);
		index++;
	    } else {

		/*
		 * If we just finished adding the first 10 entries sort them by the number of
		 * occurrences in ascending order.
		 */
		if (index == mostCommonNames.size()) {

		    // Increase the index so this is not executed again
		    index++;
		    mostCommonNames.stream().sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue));
		}

		/*
		 * At this point the entries are ordered, so for the name in the current entry
		 * check if the associated occurrence is bigger than any of the array and insert
		 * it on there.
		 */
		checkAndSetNewMax(mostCommonNames, currentEntry);
	    }
	}

	/*
	 * Now order by name descending and print
	 */

	mostCommonNames.stream().sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed())
		.forEach(c -> System.out.println("\t" + c.getKey() + ": " + c.getValue()));

    }

    /*
     * getPivotIndex: Selects a pivot element in the ArrayList, puts it in the
     * correct place and orders the other elements to the left and right
     */
    private int getPivotIndex(List<Map.Entry<String, Integer>> mostCommonNames, int firstIndex, int lastIndex) {

	// Set the pivot to the last elements
	Map.Entry<String, Integer> pivot = mostCommonNames.get(lastIndex);
	int i = firstIndex;

	/*
	 * Re-arrange elements so the one with lesser occurrences are to the left of the
	 * pivot and bigger ones are to the right.
	 */
	for (int j = firstIndex; j < lastIndex; j++) {

	    // If a value is smaller than the pivot
	    if (mostCommonNames.get(j).getValue() <= pivot.getValue()) {

		Map.Entry<String, Integer> aux = mostCommonNames.get(i);

		/*
		 * Swap places moving the smaller occurrences to the beginning of the array
		 */
		mostCommonNames.set(i, mostCommonNames.get(j));
		mostCommonNames.set(j, aux);
		i++;
	    }
	}

	// Finally swap the last element with the pivot
	mostCommonNames.set(lastIndex, mostCommonNames.get(i));
	mostCommonNames.set(i, pivot);

	// Return the new index of the pivot
	return i;
    }

    private void insertNewCommonName(List<Map.Entry<String, Integer>> mostCommonNames,
	    Map.Entry<String, Integer> newCommonName, int idx) {

	for (int j = 0; j < idx; j++)
	    mostCommonNames.set(j, mostCommonNames.get(j + 1));

	mostCommonNames.set(idx, newCommonName);
    }

    /*
     * checkAndSetNewMax: Modified Insertion Sort algorithm to check if the
     * nameEntry's associated occurrence is bigger than any in the ArrayList,
     * removing the smallest occurrence element and making room for it to be
     * inserted in the appropriate position.
     */
    private void checkAndSetNewMax(List<Map.Entry<String, Integer>> mostCommonNames,
	    Map.Entry<String, Integer> nameEntry) {

	/*
	 * If the first element is equal or greater than the new one, don't bother
	 * checking the rest and leave.
	 */
	if (nameEntry.getValue() <= mostCommonNames.get(0).getValue())
	    return;

	/*
	 * If the new element is greater or equal to the last element in the list, we
	 * need to move everything to the left to make room for it as the last element.
	 * Then return.
	 */
	if (nameEntry.getValue() >= mostCommonNames.get(mostCommonNames.size() - 1).getValue()) {
	    insertNewCommonName(mostCommonNames, nameEntry, mostCommonNames.size() - 1);
	    return;
	}

	/*
	 * Start looping from the second element, since we know the first is smaller.
	 */
	for (int i = 1; i < mostCommonNames.size() - 1; i++) {

	    /*
	     * If the current element is smaller, check if the next one is bigger.
	     */
	    if (nameEntry.getValue() >= mostCommonNames.get(i).getValue()) {

		if (nameEntry.getValue() < mostCommonNames.get(i + 1).getValue()) {

		    /*
		     * Insert the new value after the smaller and before the greater; and then
		     * leave.
		     */
		    insertNewCommonName(mostCommonNames, nameEntry, i);
		    break;
		}
	    }

	}
    }

    /*
     * printMostCommonNames: Print the keys of the highest values
     */
    public void printMostCommonNames(NameType type) {

	switch (type) {
	case FIRSTNAME:
	    getMostCommonNames(firstNames);
	    break;
	case LASTNAME:
	    getMostCommonNames(lastNames);
	    break;
	default:
	    System.out.println("Invalid name type provided.");
	}
    }

    /*
     * printModifiedNames: Prints a list with an arbitrary number of full names made
     * up with First and Last names from the original list loaded from the input
     * file. The new list and the old list should not share any full names.
     */
    public void printModifiedNames() {

	/*
	 * Load temporary ArrayLists for first and last names from the original list.
	 */
	List<String> tmpFirstNames = new ArrayList<>();
	List<String> tmpLastNames = new ArrayList<>();

	for (String s : origNames) {
	    String[] tmp = s.split(", ");
	    tmpFirstNames.add(tmp[1]);
	    tmpLastNames.add(tmp[0]);
	}

	/*
	 * Create arrays with the possible indexes for each type of name.
	 */
	List<Integer> idxFN = new ArrayList<>();
	List<Integer> idxLN = new ArrayList<>();

	for (int i = 0; i < nbrOfOrigNames; i++) {
	    idxFN.add(i);
	    idxLN.add(i);
	}

	/*
	 * Loop thru the Array of indexes; access the first name and last name; validate
	 * the full name created is not in the original list and add it to the new list.
	 */
	List<String> modifiedNames = new ArrayList<>();
	int modNamesCnt = 0;

	while (modNamesCnt < nbrOfOrigNames - 1) {

	    /*
	     * Shuffle the indexes & create modified names
	     */
	    Collections.shuffle(idxFN);
	    Collections.shuffle(idxLN);

	    /*
	     * Loop through the arrays of randomized indexes until the iterator reaches the
	     * end or until the list has the requested number of modified names.
	     */
	    for (int i = 0; i < nbrOfOrigNames && modNamesCnt < nbrOfOrigNames - 1; i++) {

		String modName = tmpLastNames.get(idxFN.get(i)) + ", " + tmpFirstNames.get(idxLN.get(i));

		/*
		 * If the modified name does not exist in the original list, it's valid so add
		 * it to the modified names list.
		 */
		if (!origNames.contains(modName)) {
		    modifiedNames.add(modName);
		    modNamesCnt++;
		}
	    }
	}

	// Print the list of modified names
	modifiedNames.stream().forEach(c -> System.out.println("\t" + c));
    }
}