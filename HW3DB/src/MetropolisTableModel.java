/**
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;


/**
* Extents the AbstractTableModel to create a model for Metropolis table with 3 columns (Metropolis, Continents, Population).
* Required connection to a database. 
* Support adding a new row, and searching for an existing row 
*/
public class MetropolisTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String account = "ccs108hkhaitan";
	private static String password = "weighize";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_hkhaitan";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	private static String[] columns = {"Metropolis", "Continents", "Population"};

	/**
	* Establishes the connection to the account, set the database to be used, initializes the ResultSet variable belonging to this class.    
	*/
	public MetropolisTableModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection( "jdbc:mysql://" + server, account, password);

			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the number of columns in the model. A JTable uses this method to determine how many columns it should create and display by default. 
	 */
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	/**
	 *  Returns the number of rows in the model. A JTable uses this method to determine how many rows it should display. 	
	 */
	@Override
	public int getRowCount() {
		if(rs != null) {
			try {
				rs.last();
				return rs.getRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * Returns a  name for the column based on the column number. 0 =  Metropolis, 1 = Continents, 2 = Population
	 * @param column = column number for which the name is returned.
	 */
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	/**
	 * Returns the value for the cell at columnIndex and rowIndex. 
	 * @param	rowIndex - the row whose value is to be queried
	 * @param   columnIndex - the column whose value is to be queried
	 * @return  the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(int row, int column) {
		try {
			rs.absolute(row + 1);
			return rs.getObject(column + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Searches the database based on the given parameters
	 * @param metropolis : This value is used to search the metropolis field of the database
	 * @param continent : This value is used to search the continent field of the database
	 * @param population : This value is used to search the population field of the database. Should contain valid integer values.  
	 * @param ExactStrings : True = Only Accept Exact Match Strings for metropolis, continent and population fields. False = Accept Partial Strings.
	 * @param popType : 1 would invoke "Greater Than" comparison against the population, 2 would invoke "Less Than" comparison against population, others would be don't care. 
	 */
	public void search(String metropolis, String continent, String population, boolean ExactStrings, int popType) {
//		searchPartial(metropolis,continent,population);
//		searchSafely(str);
		searchSmart(metropolis,continent,population,ExactStrings, popType);
		fireTableDataChanged();
//		System.out.println("done");
	}
	
	protected void searchForAdd(String str1, String str2, String str3, int rowCount) {

		String query;
		if (str3.length()!=0){
			query = "SELECT DISTINCT * FROM metropolises WHERE " + "metropolis = \"" + str1 + "\" AND " + "continent = \"" + str2 +"\" AND " + "population = \"" + str3 +"\"";
		}else{
			query = "SELECT DISTINCT * FROM metropolises WHERE " + "metropolis = \"" + str1 + "\" AND " + "continent = \"" + str2 +"\" AND " + "population IS NULL";
		}
//		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void searchSmart(String str1, String str2, String str3, boolean exact, int popSearch) {
		String query = "SELECT * FROM metropolises WHERE ";
		if(exact) {
			if(str1.length() != 0) query = query + "metropolis = \"" + str1 +"\" AND ";
			if(str2.length() != 0) query = query + "continent = \"" + str2 +"\" AND ";
		}else{
			if(str1.length() != 0) query = query + "metropolis LIKE CONCAT('%', ?, '%') AND ";
			if(str2.length() != 0) query = query + "continent LIKE CONCAT('%', ?, '%') AND ";
		}
		if(str3.length() != 0) {
			if(popSearch == 1) {
				query = query + "population > \"" + str3 + "\"";
			}else if(popSearch == 2){
				query = query + "population < \"" + str3 + "\"";
			}else{		
				query = query + "population = \"" + str3 +"\"";
			}
		}else{
			query = query + "continent NOT LIKE 'sdfsdfdsfadssdsdkj'";
		}
//		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			if(!exact){
				int i=1;
				if(str1.length() != 0) {
					stmtPrep.setString(i, str1); 
					i++;
				}
				if(str2.length() != 0) stmtPrep.setString(i, str2);	
			}
			rs = stmtPrep.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a row with the given values and publishes the added row to the table
	 * @param str1 : Metropolis column data
	 * @param str2 : Continents column data
	 * @param str3 : Is stored as a BigInteger in the "Population column"
	 */
	public void addRow(String str1, String str2, String str3){
//		Integer pop;
	//	if(str3 != null) Integer.parseInt(str3);
		int count = AddSafely(str1, str2, str3);
		searchForAdd(str1, str2, str3, count);
		fireTableDataChanged();
	}
	
	protected int AddSafely(String str1, String str2, String str3) {
		
		String update = "INSERT INTO metropolises VALUES(\"";
		update = update +  str1 + "\"";
		update = update + ",\"" + str2 + "\"";
		if(str3.length() != 0) {
			update = update + ",\"" + str3 + "\"";
		}else{
			update = update + ",NULL";
		}
		update = update + ")";
//		System.out.println(update);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(update);
			int count = stmtPrep.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
