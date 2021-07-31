/**
 * 
 */
package test.java.arielrodriguez.peoplenames;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import main.java.arielrodriguez.peoplenames.PeopleNames;

/**
 * @author Ariel Rodriguez
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeopleNamesArgumentsTest {

	@Test
	@DisplayName("Valid arguments")
	@Order(1)
	void validArgs() {
				
		/*
		 * Call the argument validation
		 * with valid parameters.
		 */
		String[] args = {"src/test/resources/coding-test-data.txt", "50"};
		
		assertTrue(PeopleNames.validateArguments(args));
		
	}
	
	@Test
	@DisplayName("Invalid Number of arguments")
	@Order(2)
	void invalidNbrArgs() {
				
		/*
		 * Call the argument validation
		 * with no parameters.
		 */
		String[] args = {};
		
		assertFalse(PeopleNames.validateArguments(args));
		
		/*
		 * Call the argument validation
		 * with only one parameter.
		 */
		String [] args1 = {"Example"};
		
		assertFalse(PeopleNames.validateArguments(args1));
		

		/*
		 * Call the argument validation
		 * with three parameters.
		 */
		String [] args2 = {"Example","3","Example2"};
		
		assertFalse(PeopleNames.validateArguments(args2));
	}
	
	@Test
	@DisplayName("Invalid Order of Arguments")
	@Order(3)
	void invalidOrderOfArgs() {
				
		/*
		 * Call the argument validation
		 * with invalid parameters.
		 */
		String[] args = {"1","/Example"};
		
		assertFalse(PeopleNames.validateArguments(args));
	}

}