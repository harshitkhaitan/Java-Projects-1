//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for(int y=0;y<this.grid[0].length;y++){
			while(all_true(y)) {
				clear_row(y);
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return this.grid; // YOUR CODE HERE
	}
	
	private boolean all_true(int y){
		for (int x=0; x<this.grid.length; x++){
			if (this.grid[x][y] == false) return false;
		}
		return true;
	}
	
	private void clear_row(int y){
		for(int i=0; i<this.grid.length; i++){
			for(int j=y; j<this.grid[i].length-1; j++){
				this.grid[i][j] = this.grid[i][j+1];
			}
			this.grid[i][this.grid[i].length-1] = false; 
		}
	}
}
