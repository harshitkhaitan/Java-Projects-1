import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class AppearancesTest {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	@Test
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
//		System.out.println("Test1 : a " + a);
		assertEquals(3, Appearances.sameCount(a, b));
//		System.out.println("Test 1 : a " + a);
	}
	
	@Test
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	
	// Add more tests
	@Test
	public void mytestSameCount3() {
		List<String> a = stringToList("abccdfa");
		List<String> b = stringToList("cccbbacpl");
		List<Integer> c = Arrays.asList(1,3,5,7);
		List<Integer> d = Arrays.asList(2,4,6,8);
		assertEquals(0,Appearances.sameCount(a,b));
		assertEquals(0,Appearances.sameCount(c,d));
	}
	
	@Test
	public void mytestSameCount4() {
		List<String> a = stringToList("abccdfa");
		List<String> b = stringToList("axccdfa");
		List<String> c = stringToList("ayccdfa");
		assertEquals(4,Appearances.sameCount(a,b));
		assertEquals(4,Appearances.sameCount(b,c));
		assertEquals(4,Appearances.sameCount(c,a));
	}

	@Test
	public void mytestSameCount5() {
		// basic List<Integer> cases
		//List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		LinkedList<Integer> a = new LinkedList<Integer>();
		a.add(1);
		a.add(3);
		a.add(3);
		a.add(1);
 		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}

	
}
