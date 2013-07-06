// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
//		System.out.println("Rows : " + this.grid.length);
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int min_pos_i=0, min_pos_j=0, max_pos_i=0, max_pos_j=0;
		int not_null = 0;
		for(int i = 0; i < this.grid.length; i++){
			for(int j = 0; j < this.grid[i].length; j++){
				if(this.grid[i][j] == ch){
					if ((i < min_pos_i) || not_null==0) min_pos_i = i ;
					if ((j < min_pos_j) || not_null==0) min_pos_j = j ;
				    if (i > max_pos_i) max_pos_i = i;
				    if (j > max_pos_j)  max_pos_j = j;
					not_null = 1;
				}
			}
		}
		max_pos_i = max_pos_i + 1; 
		max_pos_j = max_pos_j + 1;
		if (not_null == 0) max_pos_i = min_pos_i; 
//		System.out.println("Char " + ch +" Min = " + min_pos_i + "," + min_pos_j +  " Max = " + max_pos_i + "," + max_pos_j);
		return ((max_pos_i-min_pos_i)*(max_pos_j-min_pos_j)); // YOUR CODE HERE
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int length_x = this.grid.length;
		int length_y = this.grid[0].length;   // Enhancement required to support non-uniform arrays.
		int max_offset = 0;
		int totalCount = 0;
		
		for(int i = 0; i < this.grid.length; i++){
			for(int j = 0; j < this.grid[i].length; j++){
				max_offset = calculate_max_offset(i,j,length_x,length_y);
	//			System.out.println("i " + i + " j " + j + " Count " + (i*(length_y)+j) + " offset " + max_offset );
				if(max_offset>0){ 
					if(check_for_plus(i,j,max_offset)) totalCount++;
//					if(check_for_plus(i,j,max_offset)) System.out.println(" True !"); 
				}
			}
		}
					
		return totalCount; // YOUR CODE HERE
	}
	
	// My methods
	private int calculate_max_offset(int i, int j, int length_x, int length_y){
		int offset = 0;
		int init = 0;
		if ((length_x-i-1) < offset || init == 0) {
			offset = length_x -i -1;
			init = 1;
		}
		if ((length_y-j-1) < offset || init == 0) {
			offset = length_y -j -1;
			init = 1;
		}
		if (i < offset || init == 0) {
			offset = i;
			init = 1;
		}
		if (j < offset || init == 0) {
			offset = j;
			init = 1;
		}
		return offset;
	}
	
	private boolean check_for_plus(int i, int j, int max_offset){
		boolean result = false;
		char nn, ss, ee, ww;
//		char nw, ne, se, sw;
		char me = this.grid[i][j];
		int jw, je, in, is;
		for (int offset=1;offset<=max_offset;offset++){
			in = i - offset;
			is = i + offset;
			jw = j - offset;
			je = j + offset;

			nn = this.grid[in][j];
			ss = this.grid[is][j];
			ww = this.grid[i][jw];
			ee = this.grid[i][je];

//			System.out.println(" offet " + offset + " i " + i + " j " + j + " nn " + nn + " ss " + ss + " ww " + ww  + " ee " + ee);
			
			// Check skew
			if ((me == nn) || (me == ee) || (me == ss) || (me == ww)) {
				if ((me == nn) && (me == ee) && (me == ss) && (me == ww)) {
					result = true;
				}else{
					result = false;
					return result;
				}
			}
	//		nw = this.grid[in][jw];
	//		ne = this.grid[in][je];
	//		se = this.grid[is][je];
	//		sw = this.grid[is][jw];
			
			// Check Corners
			//if ((me == nw) || (me == ne) || (me == se) || (me == sw)) {
			//	return result;
			//}	

		}	

		// Check for violations outside max-offset
		in = i - (max_offset + 1);
		is = i + (max_offset + 1);
		jw = j - (max_offset + 1);
		je = j + (max_offset + 1);

		char not_me = 0;
		if (me=='0') not_me ='1';
		nn = not_me;
		ss = not_me;
		ww = not_me;
		ee = not_me;
		if (in > 0) nn = this.grid[in][j];
		if (is < this.grid.length) ss = this.grid[is][j];
		if (jw > 0) ww = this.grid[i][jw];
		if (je < this.grid[i].length) ee = this.grid[i][je];

		if ((me == nn) || (me == ee) || (me == ss) || (me == ww)) result = false;
		
				return result;
	}
	
}
