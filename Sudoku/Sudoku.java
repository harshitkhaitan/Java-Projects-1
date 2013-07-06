

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] my_easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 3 0 2",
	"2 8 7 4 0 3 9 1 0",
	"9 3 5 0 8 1 4 0 7",
	"3 9 1 0 0 6 5 0 0",
	"5 4 6 1 3 2 7 0 8",
	"7 2 8 9 0 0 1 3 6",
	"8 1 9 6 4 7 2 5 3",
	"6 7 3 5 2 9 8 4 1",
	"4 5 2 3 1 8 6 7 9");

	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
//		sudoku = new Sudoku(my_easyGrid);
			
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	private int grid[][];
	private int first_grid[][];
	private int spot_index;	
	private ArrayList<Spot> spots;
	private long starttime;

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
//		grid = new HashSet<Integer>;
		grid = new int[SIZE][SIZE];
		spots = new ArrayList<Spot>();
		spot_index = 0;
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				grid[i][j] = ints[i][j];
			}
		}

		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				if (grid[i][j] == 0){
					Spot my_spot = new Spot(i,j);
					int k;
					for(k=0; k<spots.size(); k++){
						if(spots.get(k).getSolutions() >= my_spot.getSolutions()){
							break;
						}
					}
					spots.add(k,my_spot);					
				}		
			}
		}
	}
		
	public Sudoku(String str) {
		
		String[] lines = str.split("\\n");
		grid = Sudoku.stringsToGrid(lines);
		spots = new ArrayList<Spot>();
		spot_index = 0;
	
		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				if (grid[i][j] == 0){
					Spot my_spot = new Spot(i,j);
					int k;
					for(k=0; k<spots.size(); k++){
						if(spots.get(k).getSolutions() >= my_spot.getSolutions()){
							break;
						}
					}
					spots.add(k,my_spot);					
				}		
			}	
		}		
	}
	
	
	private class Spot{
		private int i,j;
		private int solutions;
		private HashSet<Integer> values;
		
		public Spot(int i, int j){
			this.i = i;
			this.j = j;
			find_init_solutions();
		}			
		
		public void find_init_solutions(){
			values = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			int size_sqrt = (int) Math.ceil(Math.sqrt(SIZE));
			int i_low = (int) (Math.floor(i/size_sqrt))*size_sqrt;
			int j_low = (int) (Math.floor(j/size_sqrt))*size_sqrt;
			for (int a=0;a<SIZE;a++){
				values.remove(grid[a][j]);
				values.remove(grid[i][a]);
				int a_low = (int) Math.floor(a/size_sqrt);
				int b_low = (int) Math.floor(a%size_sqrt);
				values.remove(grid[i_low+a_low][j_low+b_low]);
			}
		
		}
		
		public void update_solutions(){
			values = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			int size_sqrt = (int) Math.ceil(Math.sqrt(SIZE));
			int i_low = (int) (Math.floor(i/size_sqrt))*size_sqrt;
			int j_low = (int) (Math.floor(j/size_sqrt))*size_sqrt;
			for (int a=0;a<SIZE;a++){
				values.remove(grid[a][j]);
				values.remove(grid[i][a]);
				int a_low = (int) Math.floor(a/size_sqrt);
				int b_low = (int) Math.floor(a%size_sqrt);
				values.remove(grid[i_low+a_low][j_low+b_low]);
			}
		}
			
		public void updateGrid(int x){
			grid[i][j] = x;
		}

		public void cleanGrid(){
			grid[i][j] = 0;
		}

		public int getSolutions() {
			solutions = values.size();
			return solutions;
		}
		
	}
	
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		if (starttime == 0) starttime = System.currentTimeMillis();
		
		int result=0;
		if(spot_index == spots.size()){
			spot_index = spot_index - 1;
			if(spots.size() != 0) result = 1;
			
			if (first_grid != null) return result;
			
			// Copy grid to first grid
			first_grid = new int[SIZE][SIZE];
			for (int i=0; i<SIZE; i++){
				for (int j=0; j<SIZE; j++){
					first_grid[i][j] = grid[i][j];
				}
			}
						
			return result;
		}

		int my_index = spot_index;
		Spot my_spot = spots.get(my_index);
		my_spot.update_solutions();
			
		for(int value: my_spot.values){
			my_spot.updateGrid(value);
			spot_index = spot_index + 1;
			result = result + solve();
			my_spot.cleanGrid();
			if(result >= 100) return result;
		}

		spot_index = spot_index - 1;

		this.SanityCheck();
		return result; // YOUR CODE HERE
	}
	
	private void SanityCheck(){
		for(int i=0; i<SIZE; i++){
			HashSet<Integer> set1 = new HashSet<Integer>();
			HashSet<Integer> set2 = new HashSet<Integer>();  
			for(int j=0;j<SIZE;j++){
				if (set1.contains(grid[i][j]) && grid[i][j] != 0){
					System.out.println("Sanity Falure: Writing existing value into a given row: " + i + this.toString());
				}else{
					set1.add(grid[i][j]);
				}
				if (set2.contains(grid[j][i]) && grid[j][i] != 0){
					System.out.println("Sanity Falure: Writing existing value into a given Column: " + i + this.toString());
				}else{
					set2.add(grid[j][i]);
				}
			}
		}
	}
	


	@Override
	public String toString() {
		if(grid == null) return "";
		if(grid.length != SIZE) return "";
		if(grid[0].length != SIZE) return "";
		StringBuffer result = new StringBuffer();
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				result = result.append(grid[i][j]);
				result = result.append(" ");
			}
			result = result.append("\n"); 
		}
		return result.toString();
	}

	public String getSolutionText() {
	if(first_grid == null) return "";
	if(first_grid.length != SIZE) return "";
	if(first_grid[0].length != SIZE) return ""; 
	StringBuffer result = new StringBuffer();
	for(int i=0; i<SIZE; i++){
		for(int j=0; j<SIZE; j++){
			result = result.append(first_grid[i][j]);
			result = result.append(" ");
		}
		result = result.append("\n"); 
	}
	return result.toString();
}

	
	public long getElapsed() {
		return (System.currentTimeMillis() - starttime); // YOUR CODE HERE
	}

}
