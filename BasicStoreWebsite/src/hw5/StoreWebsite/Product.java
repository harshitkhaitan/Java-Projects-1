package hw5.StoreWebsite;

public class Product {
	
	private String ProductID;
	private String ProductName;
	private String Imagefile;
	private double Price;
	private int quantity; 
	
	public Product(String pID, String pName, String iFile, double price){
		this.ProductID = pID;
		this.ProductName = pName;
		this.Imagefile = iFile;
		this.Price = price;
		this.quantity = 1;
	}

	public String getProductID() {
		return ProductID;
	}

	public String getProductName() {
		return ProductName;
	}

	public String getImagefile() {
		return Imagefile;
	}

	public double getPrice() {
		return Price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void incrQuantity() {
		this.quantity++;
	}

}
