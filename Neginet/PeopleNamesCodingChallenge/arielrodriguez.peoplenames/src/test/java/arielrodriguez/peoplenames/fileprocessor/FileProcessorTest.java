/**
 * 
 */
package test.java.arielrodriguez.peoplenames.fileprocessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import main.java.arielrodriguez.peoplenames.fileprocessor.FileProcessor;
import main.java.arielrodriguez.peoplenames.fileprocessor.FileProcessor.NameType;

/**
 * @author Ariel Rodriguez
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileProcessorTest {
	
	FileProcessor fp;
	
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	/**
	 * setUp()
	 * Reassign the standard output stream to a new PrintStream with a ByteArrayOutputStream, so we
	 * can validate it in our tests. Also instantiate the FileProcessor class.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	@DisplayName("Reassign stdOut to ByteArrayOutputStream, read names from file and load internal structures.")
	void setUp() throws Exception {
		
		System.setOut(new PrintStream(outputStreamCaptor));
		
		fp = new FileProcessor("src/test/resources/coding-test-data.txt", 25);
	}

	/*
	 * testCardinality()
	 * Validates the correct numbers are printed for the specified file.
	 */
	@Test
	@DisplayName("Cardinality Validation")
	@Order(1)
	void testCardinality() {
		
		fp.printCardinality();
		
		assert(outputStreamCaptor.toString().contains("Full names : 48419"));
		assert(outputStreamCaptor.toString().contains("Last names : 469"));
		assert(outputStreamCaptor.toString().contains("First names : 3007"));
	}
	
	/*
	 * testCommonLastNames()
	 * Validates the correct names are printed for the specified file.
	 */
	@Test
	@DisplayName("10 Most Common Last Names Validation")
	@Order(2)
	void testCommonLastNames() {
		
		fp.printMostCommonNames(NameType.LASTNAME);		

		assert(outputStreamCaptor.toString().contains("Terry: 129"));
		assert(outputStreamCaptor.toString().contains("Romaguera: 128"));
		assert(outputStreamCaptor.toString().contains("Ortiz: 135"));
		assert(outputStreamCaptor.toString().contains("Lang: 136"));
		assert(outputStreamCaptor.toString().contains("Johns: 128"));
		assert(outputStreamCaptor.toString().contains("Hills: 130"));
		assert(outputStreamCaptor.toString().contains("Hilll: 134"));
		assert(outputStreamCaptor.toString().contains("Becker: 128"));
		assert(outputStreamCaptor.toString().contains("Batz: 127"));
		assert(outputStreamCaptor.toString().contains("Barton: 143"));
	}
	
	/*
	 * testCommonFirstNames()
	 * Validates the correct names are printed for the specified file.
	 */
	@Test
	@DisplayName("10 Most Common First Names Validation")
	@Order(3)
	void testCommonFirstNames() {

		fp.printMostCommonNames(NameType.FIRSTNAME);

		assert(outputStreamCaptor.toString().contains("Ulices: 29"));
		assert(outputStreamCaptor.toString().contains("Tara: 32"));
		assert(outputStreamCaptor.toString().contains("Stephania: 31"));
		assert(outputStreamCaptor.toString().contains("Milo: 29"));
		assert(outputStreamCaptor.toString().contains("Madyson: 29"));
		assert(outputStreamCaptor.toString().contains("Keon: 31"));
		assert(outputStreamCaptor.toString().contains("Kayley: 29"));
		assert(outputStreamCaptor.toString().contains("Kaycee: 30"));
		assert(outputStreamCaptor.toString().contains("Charlotte: 29"));
		assert(outputStreamCaptor.toString().contains("Andreanne: 31"));
	}

	/*
	 * testModifiedNames()
	 * Validates the correct first and last names are printed for the specified file.
	 * The full names produced are random but the individual unique first and last names
	 * will be the same. 
	 */
	@Test
	@DisplayName("List of Modified Names Validation")
	@Order(4)
	@Disabled
	void testModifiedNames() {

		fp.printModifiedNames();

		//Some Last Names
		assert(outputStreamCaptor.toString().contains("Koch"));		
		assert(outputStreamCaptor.toString().contains("Stoltenberg"));	
		assert(outputStreamCaptor.toString().contains("Bahringer"));	
		assert(outputStreamCaptor.toString().contains("Stanton"));		
		assert(outputStreamCaptor.toString().contains("Rogahn"));		
		assert(outputStreamCaptor.toString().contains("Cartwright"));	
		assert(outputStreamCaptor.toString().contains("Tillman"));		
		assert(outputStreamCaptor.toString().contains("McLaughlin"));	
		assert(outputStreamCaptor.toString().contains("Hills"));		
		assert(outputStreamCaptor.toString().contains("Tromp"));		
		assert(outputStreamCaptor.toString().contains("Dickinson"));	
		assert(outputStreamCaptor.toString().contains("Marvin"));		
		assert(outputStreamCaptor.toString().contains("Bradtke"));		
		assert(outputStreamCaptor.toString().contains("Fisher"));
		
		//Some First Names
		assert(outputStreamCaptor.toString().contains("Nikko"));
		assert(outputStreamCaptor.toString().contains("Mckenna"));
		assert(outputStreamCaptor.toString().contains("Stanley"));
		assert(outputStreamCaptor.toString().contains("Garfield"));
		assert(outputStreamCaptor.toString().contains("Sonya"));
		assert(outputStreamCaptor.toString().contains("Anita"));
		assert(outputStreamCaptor.toString().contains("Bethel"));
		assert(outputStreamCaptor.toString().contains("Agustina"));
		assert(outputStreamCaptor.toString().contains("Colby"));
		assert(outputStreamCaptor.toString().contains("Gertrude"));
		assert(outputStreamCaptor.toString().contains("Norma"));
		assert(outputStreamCaptor.toString().contains("Ryley"));
		assert(outputStreamCaptor.toString().contains("Mariah"));
	}
	
	@AfterEach
	@DisplayName("Reassign stdOut to standardOut")
	public void tearDown() {
	    System.setOut(standardOut);
	}

}