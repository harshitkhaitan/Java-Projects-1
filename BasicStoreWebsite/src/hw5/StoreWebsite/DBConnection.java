package hw5.StoreWebsite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	
	private static String account = "ccs108hkhaitan";
	private static String password = "weighize";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_hkhaitan";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	

	public DBConnection() {
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


	public Connection getCon() {
		return con;
	}
	
	public void destroy() {
		try {
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public ProductList GetAllEntries(){
//		ProductList prods = new ProductList();
//		try {
//			PreparedStatement stmtPrep = con.prepareStatement("Select * FROM products");
//			rs = stmtPrep.executeQuery();
//		      while (rs.next()) {
//		            String pID = rs.getString("productid");
//		            String name = rs.getString("name");
//		            String imagefile = rs.getString("imagefile");
//		            double price = rs.getDouble("price");
//		            prods.addEntry(pID, name, imagefile, price);
//		            System.out.println(pID + "\t" + name +
//		                               "\t"+ imagefile +
//		                               "\t" + price);
//		      }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return prods;	
//	}
	
}
