package hw5.StoreWebsite;

import java.io.IOException;
import java.sql.Connection;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servContext = getServletContext();
		Connection conn = (Connection) servContext.getAttribute("Connection");
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
	
		String Mode = request.getParameter("Update");
		if(Mode.equals("true")){
			ProductList RemoveList = new ProductList();
			for(Product item: cart.getProds().getProducts()){
				String pID = item.getProductID();
				String quantity = request.getParameter(pID);
				Integer pQuantity = Integer.parseInt(quantity);
				if(pQuantity < 1){
					RemoveList.addEntry(item);
				}else{
					item.setQuantity(pQuantity);
				}
			}
			for(Product item: RemoveList.getProducts()){
				cart.remove(item);
			}
		}else{
			Mode = "true";
			String pID = request.getParameter("pID");
			if(cart == null) {
				cart = new ShoppingCart();
				session.setAttribute("cart", cart);
			}
			cart.AddToCart(pID , conn);			
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("cart.jsp");
		dispatch.forward(request, response);			
	}

}
