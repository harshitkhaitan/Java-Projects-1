import static org.junit.Assert.*;

import org.junit.Test;


public class CrackerTest {

	@Test
	public void testGenerator() {
		Cracker testCracker = new Cracker("a");
		assertTrue(testCracker.getGeneratedhashedPassword().equals("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8"));
		
		testCracker = new Cracker("fm");
		assertTrue(testCracker.getGeneratedhashedPassword().equals("adeb6f2a18fe33af368d91b09587b68e3abcb9a7"));

		testCracker = new Cracker("a!");
		assertTrue(testCracker.getGeneratedhashedPassword().equals("34800e15707fae815d7c90d49de44aca97e2d759"));

		testCracker = new Cracker("xyz");
		assertTrue(testCracker.getGeneratedhashedPassword().equals("66b27417d37e024c46526c2f6d358a754fc552f3"));
	}

	@Test
	public void testCracking() {
		Cracker testCracker = new Cracker("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8",1,3);
		assertTrue(testCracker.getGeneratedPassword().equals("a"));
		
		testCracker = new Cracker("adeb6f2a18fe33af368d91b09587b68e3abcb9a7",3,4);
		assertTrue(testCracker.getGeneratedPassword().equals("fm"));

		testCracker = new Cracker("34800e15707fae815d7c90d49de44aca97e2d759",1,4);
		assertTrue(testCracker.getGeneratedPassword().equals(""));

		testCracker = new Cracker("66b27417d37e024c46526c2f6d358a754fc552f3",3,40);
		assertTrue(testCracker.getGeneratedPassword().equals("xyz"));
	}

}
