package hw5.StoreWebsite;

import java.sql.Connection;

public class ShoppingCart {
	private ProductList prods;
	
	public ShoppingCart(){
		prods = new ProductList();
	}
	
	public void AddToCart(String pID, Connection conn){
		for (Product prod : prods.getProducts()){
			if (prod.getProductID().equals(pID) && prod.getQuantity()>0){
				prod.incrQuantity();
				return;
			}
		}
		prods.addWithID(pID, conn);		
	}

	public ProductList getProds() {
		return prods;
	}

	public void remove(Product item) {
		prods.remove(item);
	}
	
}
