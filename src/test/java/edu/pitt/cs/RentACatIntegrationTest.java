package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import java.lang.reflect.Method;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatIntegrationTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object
	InstanceType instanceType = InstanceType.IMPL;
	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		r = RentACat.createInstance(instanceType);

        if(instanceType == InstanceType.MOCK){
            c1 = mock(Cat.class);
            c2 = mock(Cat.class);
            c3 = mock(Cat.class);
            when(c1.getId()).thenReturn(1);
            when(c1.getName()).thenReturn("Jennyanydots");
            when(c2.getId()).thenReturn(2);
            when(c2.getName()).thenReturn("Old Deuteronomy");
            when(c3.getId()).thenReturn(3);
            when(c3.getName()).thenReturn("Mistoffelees");
        } 
		else{
            c1 = Cat.createInstance(instanceType, 1, "Jennyanydots");
            c2 = Cat.createInstance(instanceType, 2, "Old Deuteronomy");
            c3 = Cat.createInstance(instanceType, 3, "Mistoffelees");
        }
		stdout = System.out;
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		// Second, update System.out to the PrintStream created from "out"
		// TODO: Fill in.  Refer to the textbook chapter 14.6 on Testing System Output.
	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() {
		try{
			Method getCatMethod = r.getClass().getDeclaredMethod("getCat", int.class);
			getCatMethod.setAccessible(true);
			Cat result = (Cat)getCatMethod.invoke(r, 2);
			assertNull(result);
			assertEquals("Invalid cat ID." + newline, out.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() {
		try{
			r.addCat(c1);
			r.addCat(c2);
			r.addCat(c3);
			Method getCatMethod = r.getClass().getDeclaredMethod("getCat", int.class);
			getCatMethod.setAccessible(true);
			Cat result = (Cat)getCatMethod.invoke(r, 2);
			assertNotNull(result);
			assertEquals(2, result.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		assertEquals("", r.listCats());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		String validList = "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n";
		assertEquals(validList, r.listCats());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 */
	@Test
	public void testRenameFailureNumCats0() {
		assertFalse(r.renameCat(2, "Garfield"));
		assertEquals("Invalid cat ID." + newline, out.toString());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 */
	@Test
	public void testRenameNumCat3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		assertTrue(r.renameCat(2, "Garfield"));
		assertEquals("Garfield", c2.getName());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 */
	@Test
	public void testRentCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		out.reset();
		assertTrue(r.rentCat(2));
		assertTrue(c2.getRented());
		assertEquals("Old Deuteronomy has been rented." + newline, out.toString());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays rented.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		r.rentCat(2);
		out.reset();
		assertFalse(r.rentCat(2));
		assertTrue(c2.getRented());
		assertEquals("Sorry, Old Deuteronomy is not here!" + newline, out.toString());
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		r.rentCat(2);
		out.reset();
		boolean returned = r.returnCat(2);
		assertTrue(returned);
		assertFalse(c2.getRented());
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays not rented.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		out.reset();
		assertFalse(r.returnCat(2));
		assertFalse(c2.getRented());
		assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
	}

}