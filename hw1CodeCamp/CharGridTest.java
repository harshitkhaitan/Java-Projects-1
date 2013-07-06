
// Test cases for CharGrid -- a few basic tests are provided.
import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	@Test
	public void mytestCharArea3() {
		char[][] grid = new char[][] {
				{'c', 'a', 'b'},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'},
				{'a', 'a', ' '},
				{'b', ' ', ' '},
				{' ', ' ', 'a'}

			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(18, cg.charArea('a'));
		assertEquals(15, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
		assertEquals(0, cg.charArea('d'));
		assertEquals(15, cg.charArea(' '));
	}
	
	@Test
	public void mytestCountPlus1() {
		char[][] grid = new char[][] {
				{'c', 'a', 'b', 'b', 'a'},
				{'c', ' ', 'b', 'b', 'b'},
				{'b', 'b', 'a', 'b', 'b'},
				{'c', 'a', 'a', 'a', 'c'},
				{'c', 'a', 'a', 'b', 'a'},
				{'c', 'a', 'b', 'b', 'a'}

			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(2, cg.countPlus());

	}
	
	@Test
	public void mytestCountPlus2() {
		char[][] grid = new char[][] {
				{'c', 'a', 'b', 'b', 'a'},
				{'c', ' ', 'b', 'a', 'b'},
				{'b', 'b', 'a', 'b', 'b'},
				{'c', 'a', ' ', 'a', 'c'},
				{'c', 'a', 'a', 'b', 'a'}
	
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());

	}
	

	@Test
	public void mytestCountPlus3() {
		char[][] grid = new char[][] {
				{'c', 'c', 'b', 'b', 'a'},
				{'c', 'c', 'c', 'a', 'b'},
				{'b', 'c', 'c', 'c', 'b'},
				{'c', 'a', 'c', 'a', 'c'},
				{'c', 'a', 'a', 'b', 'a'}
	
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(2, cg.countPlus());

	}

	@Test
	public void mytestCountPlus4() {
		char[][] grid = new char[][] {
				{'c', 'a', 'b', 'b', 'a'},
				{'c', '1', 'b', 'a', 'b'},
				{'1', '1', '1', 'b', 'b'},
				{'c', '1', ' ', 'a', 'c'},
				{'c', 'a', 'a', 'b', 'a'}
	
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());

	}

	@Test
	public void mytestCountPlus5() {
		char[][] grid = new char[][] {
				{'c', 'a', '1', 'b', 'a'},
				{'c', '1', '1', '1', 'b'},
				{'1', '1', '1', '1', '1'},
				{'c', '1', '1', '1', 'c'},
				{'c', 'a', '1', 'b', 'a'}
	
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());

	}


}
