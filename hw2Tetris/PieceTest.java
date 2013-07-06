import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4, pyr5;
	private Piece s, sRotated;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		
//		for (TPoint part: pyr1.getBody()){
//			System.out.println("Body 1" + part.toString());	
//		}
		
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();
	
//		for (TPoint part: pyr3.getBody()){
//			System.out.println("Body 3" + part.toString());	
//		}
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}

	@Test
	public void testEqual() {
		assertFalse(pyr1.equals(pyr2));
		assertFalse(pyr1.equals(pyr3));
		assertFalse(pyr1.equals(pyr4));
		assertTrue(pyr1.equals(pyr1));
		assertTrue(pyr1.equals(pyr5));
	}
	
	@Test
	public void testEquals() {
//		for (TPoint part: pyr2.getBody()){
//			System.out.println("Body 3" + part.toString());	
//		}
//		for (TPoint part: pyr1.fastRotation().getBody()){
//			System.out.println("Body 3 new " + part.toString());	
//		}
		
		Piece [] pieces = Piece.getPieces();
		
		assertTrue(pieces[0].fastRotation().equals(pieces[0].computeNextRotation())); 
		assertTrue(pieces[1].fastRotation().fastRotation().equals(pieces[1].computeNextRotation().computeNextRotation()));
		
	}
}
