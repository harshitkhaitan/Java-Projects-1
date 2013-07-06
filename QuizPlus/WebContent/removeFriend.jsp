<%@ page import="web.config.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message from User <%= request.getParameter("userName") %></title>
</head>
<body>
<% 
String user = (String)  request.getSession().getAttribute("userName");
String FriendName = request.getParameter("toUser");
DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
db.removeFriend(user, FriendName);


out.println("<b>" + FriendName + " removed from your list </b>");

%>

</body>
</html>