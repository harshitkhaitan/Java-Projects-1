<%@ page language="java" import="java.sql.*" %>
<%@ page import="hw5.StoreWebsite.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%
ServletContext servContext = getServletContext();
Connection conn = (Connection) servContext.getAttribute("Connection");
String pID = request.getParameter("id");
Product prod = ProductList.getProductforID(pID, conn);
String pName = prod.getProductName();
String iName = "store-images/"+ prod.getImagefile();
Double price = prod.getPrice();
//out.println(ProductList.getNameforID(pID, conn));
out.println(pName);
%></title>
</head>
<body>
<b><font size="7"> <% out.println(pName); %> </font></b><br/>
<img src=<% out.println("\"" + iName + "\"");%> alt = "image"/><br/>
<br/>
<form action="CartServlet" method="post">
	<input type="hidden" name="Update" value="false"/>
	<input type="hidden" name="pID" value=<%out.println("\"" + pID + "\"");%> /><br /><br />
	<% out.println("$" + price.toString()); %> <input type="submit" value="Add to Cart" />
</form>
</body>
</html>