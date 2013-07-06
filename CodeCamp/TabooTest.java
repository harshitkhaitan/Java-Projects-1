// TabooTest.java
// Taboo class tests -- nothing provided.

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class TabooTest {
	@Test
	public void testnoFollow1(){
		List<Integer> int_rulelist = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		Taboo<Integer> rulelist = new Taboo<Integer>(int_rulelist) ;
		
		int testSample = 3;
		
		Set<Integer> noFollowResult = new HashSet<Integer>(); 
		noFollowResult = rulelist.noFollow(testSample);
		
		Set<Integer> noFollowCompareResult = new HashSet<Integer>(Arrays.asList(1,5)); 
		
		assertTrue(noFollowResult.equals(noFollowCompareResult));
	
		testSample = 1;
		noFollowResult = rulelist.noFollow(testSample);
//		System.out.println("noFollowResult " + noFollowResult);
		noFollowCompareResult.clear();
		noFollowCompareResult.addAll(Arrays.asList(2));
		assertTrue(noFollowResult.equals(noFollowCompareResult));
		
		testSample = 6;
		noFollowResult = rulelist.noFollow(testSample);
//		System.out.println("noFollowResult " + noFollowResult);
		noFollowCompareResult.clear();
//		noFollowCompareResult.addAll(Arrays.asList(2));
		assertTrue(noFollowResult.equals(noFollowCompareResult));

		testSample = 5;
		noFollowResult = rulelist.noFollow(testSample);
//		System.out.println("noFollowResult " + noFollowResult);
		noFollowCompareResult.clear();
//		noFollowCompareResult.addAll(Arrays.asList(2));
		assertTrue(noFollowResult.equals(noFollowCompareResult));

	}

	@Test
	public void testnoFollow2(){
		List<String> int_rulelist = Arrays.asList("abc", "bbc", "bcc", "bbc", "abc");
		Taboo<String> rulelist = new Taboo<String>(int_rulelist) ;
		
		String testSample = "bbc";
		
		Set<String> noFollowResult = new HashSet<String>(); 
		noFollowResult = rulelist.noFollow(testSample);
		
		Set<String> noFollowCompareResult = new HashSet<String>(Arrays.asList("abc","bcc")); 
		
		assertTrue(noFollowResult.equals(noFollowCompareResult));
			
	}
	
	@Test
	public void testreduce1(){
		List<Integer> int_rulelist = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		Taboo<Integer> rulelist = new Taboo<Integer>(int_rulelist) ;
	
		List<Integer> testSampleArray = new ArrayList<Integer>();
		testSampleArray.addAll(Arrays.asList(5,3,2,1,2));
				
		rulelist.reduce(testSampleArray);
		
		List<Integer> ExpectedOutputArray = Arrays.asList(5,3,2,1);
		
//		System.out.println(" Actual Output " + testSampleArray);
		assertTrue(ExpectedOutputArray.equals(testSampleArray));
		
		testSampleArray.addAll(Arrays.asList(2, 2));		
		rulelist.reduce(testSampleArray);
//		System.out.println(" Actual Output " + testSampleArray);
		assertTrue(ExpectedOutputArray.equals(testSampleArray));

		testSampleArray.clear();
		testSampleArray.addAll(Arrays.asList(1,2,2,2,2));
		rulelist.reduce(testSampleArray);
//		System.out.println(" Actual Output " + testSampleArray);
		assertTrue(Arrays.asList(1).equals(testSampleArray));

		testSampleArray.clear();
		testSampleArray.addAll(Arrays.asList(1,2,1,2,1));
		rulelist.reduce(testSampleArray);
//		System.out.println(" Actual Output " + testSampleArray);
		assertTrue(Arrays.asList(1,1,1).equals(testSampleArray));

	}
	
	@Test
	public void testreduce2(){
		List<String> int_rulelist = Arrays.asList("1", "2", "3", "11", "2", "3", "5");
		Taboo<String> rulelist = new Taboo<String>(int_rulelist) ;
	
		List<String> testSampleArray = new ArrayList<String>();
		testSampleArray.addAll(Arrays.asList("55","3","22","1","2"));
				
		rulelist.reduce(testSampleArray);
		assertTrue(Arrays.asList("55","3","22","1").equals(testSampleArray));
	}
}
