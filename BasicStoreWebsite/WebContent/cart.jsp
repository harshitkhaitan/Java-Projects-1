<%@ page language="java" import="java.sql.*" %>
<%@ page import="hw5.StoreWebsite.*"%>
<%@ page language="java" import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body>
<b><font size="5">Shopping Cart</font></b>
<form action="CartServlet" method="post">
<%
out.println("<ul>");
ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
Double total = (double) 0;
for(Product item: cart.getProds().getProducts()){
	String pID = item.getProductID();
	Integer pQuantity = item.getQuantity();
	String quantity = pQuantity.toString();
	String pName = item.getProductName();
	Double pPrice = item.getPrice();
	total = pPrice*pQuantity + total;
	String price = Double.toString(pPrice);
	String formInput = " <input type=\"text\" name=\"" + pID + "\" value=\"" + quantity + "\"/>  ";
	out.println("<li>" + formInput + pName + "," + price);
}
out.println("</ul>");
%>
<input type="hidden" name="Update" value="true"/>
<%
DecimalFormat twoD = new DecimalFormat("#.##");
Double roundTotal = Double.valueOf(twoD.format(total));
String updateCartButton = "  <input type=\"submit\" value=\"Update Cart\"/>  ";
out.println("Total: $" + roundTotal.toString() + updateCartButton);
%>
</form>
<a href="index.jsp"\>Continue Shopping</a>

</body>
</html>

