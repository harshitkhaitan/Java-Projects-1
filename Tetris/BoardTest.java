import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	@Test
	public void testSample3() {
		
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		b.commit();
		result = b.place(sRotated, 3, 6);
		assertEquals(Board.PLACE_OUT_BOUNDS, result);
		b.commit();
		result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_BAD, result);
	}

	@Test
	public void testSample4() {
		b = new Board(3, 6);
		b.commit();
		int result = b.place(pyr1, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.undo();
//		b.commit();
		result = b.place(pyr1, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.commit();
		result = b.place(pyr1, 0, 0);
		assertEquals(Board.PLACE_BAD, result);
		b.undo();
//		b.commit();
		result = b.place(pyr1, 0, 2);
//		System.out.println("before clearRows 1");
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.clearRows();
//		System.out.println("after clearRows 1");
		assertEquals(2, b.getMaxHeight());		
		b.commit();
		result = b.place(pyr1, 0, 2);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.commit();
		result = b.place(pyr3, 0, 4);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.commit();
		b.clearRows();
		assertEquals(4, b.getMaxHeight());		
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0, b.getColumnHeight(2));		
		assertEquals(0, b.getColumnHeight(0));		
		
		assertEquals(4, b.getMaxHeight());		

		assertEquals(4, b.dropHeight(pyr1, 0));
		Piece stick1 = new Piece(Piece.STICK_STR);
		assertEquals(0, b.dropHeight(stick1, 0));
		b.commit();
		result = b.place(stick1, 0, 0);
		assertEquals(Board.PLACE_OK, result);
		b.undo();
		result = b.place(stick1, 0, 0);
		assertEquals(Board.PLACE_OK, result);
		b.commit();
		result = b.place(stick1, 2, 0);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		int rows = b.clearRows();
		assertEquals(4, rows);
		assertEquals(0,b.getRowWidth(0));
		assertEquals(0,b.getRowWidth(3));
		assertEquals(0,b.getRowWidth(5));

	}

	@Test
	public void testSample5() {
		b = new Board(3, 6);
		b.commit();
		int result = b.place(pyr1, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		b.clearRows();
		assertEquals(1, b.dropHeight(pyr1, 0));	
		assertEquals(0, b.dropHeight(pyr2, 1));		
		assertEquals(1, b.dropHeight(pyr1, 0));		
		assertEquals(0, b.dropHeight(pyr4, 0));		
		b.commit();
		result = b.place(pyr2, 1, 0);
		assertEquals(Board.PLACE_OK, result);		
	}
	
}
