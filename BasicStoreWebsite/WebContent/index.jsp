<%@ page language="java" import="java.sql.*" %>
<%@ page import="hw5.StoreWebsite.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Store</title>
</head>
<body>
<b><font size="7">Student Store</font></b><br /><br />
Items available: <br />
<% 
ServletContext servContext = getServletContext();
Connection conn = (Connection) servContext.getAttribute("Connection");
//ProductList prods = (ProductList) servContext.getAttribute("Product List");
ProductList prods = ProductList.GetAllEntries(conn);
out.println("<ul>");
for(Product prod: prods.getProducts()){
	String ref = "<a href=\'store-product.jsp?id=" + prod.getProductID() + "\'>" + prod.getProductName() + "</a>";
	out.println("<li>" + ref);
}
out.println("</ul>");
%>

</body>
</html>