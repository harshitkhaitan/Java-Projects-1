// Board.java

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = false;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int Maxheight;
	
	private boolean[][] xgrid;
	private int[] xwidths;
	private int[] xheights;
	private int xMaxheight;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		
		// YOUR CODE HERE
		widths = new int[height];
		xwidths = new int[height];

		heights = new int[width];
		xheights = new int[width];

		xgrid = new boolean[width][height];
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return this.Maxheight; // YOUR CODE HERE
	}
	
//	private int getMaxHeight_sanity() {
//		int my_MaxHeight = 0;
//		for(int i=0; i<width; i++){
//			if (getColumnHeight_sanity(i) > my_MaxHeight) my_MaxHeight = getColumnHeight_sanity(i);
//		}
//		return my_MaxHeight; // YOUR CODE HERE
//	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck(int caller) {
		if (DEBUG) {
			// YOUR CODE HERE
//			if(getMaxHeight_sanity() != getMaxHeight()) System.out.print("Sanity check failed on Maxheight " + getMaxHeight_sanity() + " vs " + getMaxHeight());

			for(int i=0; i<this.Maxheight; i++){
				if(this.getRowWidth(i) != this.getRowWidth_sanity(i)) {
					System.out.print("Sanity check failed on width for row " + i + " getRowWidth "  + this.getRowWidth(i) + " getRowWidth_sanity " + getRowWidth_sanity(i) + " Caller " + caller) ;
					break;
				}
			}
			
//			System.out.println("failed here 001");
			
			int y=0;
			for(int x=0; x<width; x++){
				if(heights[x] > 0) {
					y = heights[x]-1;
					if(grid[x][y] == false) {
						System.out.println("Sanity failed on height check at x = " + x + " y = " + y ); 
					}
				}
				
				if(heights[x]>Maxheight){
					System.out.println("max height write failure : " + Maxheight + " Calculated value : " + heights[x]);
				}
			}
			
			
			
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int low=0, px;
		for(TPoint part: piece.getBody()){
			px = x + part.x;
			if((heights[px]-part.y)>low) low = heights[px] - part.y; 
		}
		return low; // YOUR CODE HERE
	}
	
//	public int dropHeight(Piece piece, int x) {
//		int low, px;
//		int i=0;
//		for(int y: piece.getSkirt()){
//			px = x + i;
//			if(low>(heights[px]-)) ;
//		}
//	}

	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
	
		return heights[x]; // YOUR CODE HERE
	}
	
//	private int getColumnHeight_sanity(int x) {
//		
//		for(int j=height-1;j>=0;j--){
//			if(this.grid[x][j] == true) return (j+1);
//		}
//		return 0; // YOUR CODE HERE
//	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return this.widths[y]; // YOUR CODE HERE
	}
	
	private int getRowWidth_sanity(int y) {
		int total = 0;
		for(int x=0;x<width;x++){
			if(this.grid[x][y] == true && heights[x]>y) total++;
		}
		 return total; // YOUR CODE HERE
	}

	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x>=grid.length || x<0) return true;
		if(y>=grid[0].length || y<0) return true;
		if(y>=heights[x]) return false;
		return grid[x][y]; // YOUR CODE HERE
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
			
		int result = PLACE_OK;
		
		// YOUR CODE HERE
		takeBackup();
		
		committed = false;
	
		// Place
		int px, py;
		for(TPoint part: piece.getBody()){
			px = x + part.x;
			py = y + part.y;
			if(px < 0 || px >= this.width) return PLACE_OUT_BOUNDS;
			if(py < 0 || py >= this.height) return PLACE_OUT_BOUNDS;
			if(grid[px][py] == true && py<heights[px]) return PLACE_BAD;

			this.widths[py]++;
			if(py >= this.heights[px]) this.heights[px] = py+1;
			if(this.heights[px] > this.Maxheight) this.Maxheight = this.heights[px];
			
			grid[px][py] = true;
			if(getRowWidth(py) == width) result = PLACE_ROW_FILLED;
		}
		
		sanityCheck(1);
		return result;
	}
	
	
//	private void copy_2d_array(boolean[][] src, boolean[][] dest, int my_width, int my_heigth){
//		for (int i=0; i<my_width; i++){
//			System.arraycopy(src[i], 0, dest[i], 0, my_heigth);
//		}
//	}
	
	private void takeBackup(){
		// Take Backup
		xgrid = new boolean[width][height];
		xwidths = new int[height];
		xheights = new int[width];
		System.arraycopy(heights, 0, xheights, 0, width);
		System.arraycopy(widths, 0, xwidths, 0, height);
		this.xMaxheight = this.Maxheight;
		for (int i=0; i<width; i++){
			System.arraycopy(grid[i], 0, xgrid[i], 0, height);
		}
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		// YOUR CODE HERE
		if(committed == true) takeBackup();
		
		
//        System.out.println(" Heights " + heights[0] + " " + heights[1] + " " + heights[2] + " MaxHeight " + Maxheight );
		
        int[] heights_new = new int[width]; 
//        int[] widths_new = new int[height];
        
		for (int y=0;y<Maxheight;y++){
			
			if (widths[y] == width) {				
				rowsCleared++;
				Maxheight--;
			}
			if ((y+rowsCleared)>=height){
				widths[y]=0;
//				widths_new[y] = 0;
			}else{
				widths[y]=widths[y+rowsCleared];
//				widths_new[y]=widths[y+rowsCleared];
			}
			for(int x=0; x<width; x++){
				if ((y+rowsCleared)>=height){
					grid[x][y] = false;
				}else{
					grid[x][y] = grid[x][y+rowsCleared];

					if((grid[x][y] == true) && (widths[y] != width)) heights_new[x] = y+1;
					
//					System.out.println("x, src " + x + " ," + (y+rowsCleared) + " dest " + y + " MaxHeight " + Maxheight + "  height " + heights_new[x]);

				}
			}
			
			if (widths[y] == width) {
				y--;
			}
		}
		
		for (int y=Maxheight;y<Maxheight+rowsCleared;y++){	
			if(y>=height) break;
			for(int x=0; x<width; x++){
				grid[x][y] = false;
				widths[y] = 0;
			}
		}
		
		heights = heights_new;
//		widths = widths_new;
//        System.out.println(" Heights " + heights[0] + " " + heights[1] + " " + heights[2] + " MaxHeight " + Maxheight + " RowsCleared " + rowsCleared);

		
		sanityCheck(2);

		
		committed = false;
		return rowsCleared;
		
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		this.Maxheight = this.xMaxheight;
		this.grid = this.xgrid;
		this.widths = this.xwidths;
		this.heights = this.xheights;
		committed = true;
//		return;
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


