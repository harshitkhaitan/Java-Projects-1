package hw5.StoreWebsite;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ProductList {
	
	private ArrayList<Product> Products;
	
	public ProductList(){
		Products = new ArrayList<Product>();
	}
	
	public void addEntry(String pID, String pName, String iFile, double price){
		Products.add(new Product(pID, pName, iFile, price));
	}

	public void addEntry(Product item) {
		Products.add(item);
	}

	public ArrayList<Product> getProducts() {
		return Products;
	}

	public void addWithID(String pID, Connection conn){
		ResultSet rs;
		try {
			PreparedStatement stmtPrep = conn.prepareStatement("Select * FROM products WHERE productid=\"" + pID + "\"");
			rs = stmtPrep.executeQuery();
		      while (rs.next()) {
		            String pName = rs.getString("name");
		            String imagefile = rs.getString("imagefile");
		            double price = rs.getDouble("price");
		            this.Products.add(new Product(pID, pName, imagefile, price));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static String getNameforID(String pID, Connection conn){
		String pName = null;
		ResultSet rs;
		try {
			PreparedStatement stmtPrep = conn.prepareStatement("Select * FROM products WHERE productid=\"" + pID + "\"");
			rs = stmtPrep.executeQuery();
		      while (rs.next()) {
		            pName = rs.getString("name");
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		}		
//		for(Product prod: Products){
//			if(prod.getProductID().equals(pID)) return prod.getProductName();
//		};
		return pName;
	}

	public static Product getProductforID(String pID, Connection conn){
		Product prod = null;
		ResultSet rs;
		try {
			PreparedStatement stmtPrep = conn.prepareStatement("Select * FROM products WHERE productid=\"" + pID + "\"");
			rs = stmtPrep.executeQuery();
		      while (rs.next()) {
		            String pName = rs.getString("name");
		            String imagefile = rs.getString("imagefile");
		            double price = rs.getDouble("price");
		            prod = new Product(pID, pName, imagefile, price);
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return prod;
	}
	
	public static ProductList GetAllEntries(Connection conn){
		ProductList prods = new ProductList();
		ResultSet rs;
		try {
			PreparedStatement stmtPrep = conn.prepareStatement("Select * FROM products");
			rs = stmtPrep.executeQuery();
		      while (rs.next()) {
		            String pID = rs.getString("productid");
		            String name = rs.getString("name");
		            String imagefile = rs.getString("imagefile");
		            double price = rs.getDouble("price");
		            prods.addEntry(pID, name, imagefile, price);
//		            System.out.println(pID + "\t" + name +
//		                               "\t"+ imagefile +
//		                               "\t" + price);
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prods;	
	}

	public void remove(Product item) {
		Products.remove(item);
	}

	
}
