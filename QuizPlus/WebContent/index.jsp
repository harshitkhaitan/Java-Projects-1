<%@ page import="web.config.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to QuizPlus</title>
</head>
<body>

<%
if(request.getParameter("status") == null){
}else if(request.getParameter("status").equals("login")){
	out.println("<b><FONT COLOR=#009900>Please Log in to continue</FONT><br></b>");
	out.println("&nbsp");
}
%>


<br>
<form action="LoginServlet" method="post">
	<b>User Name:</b><input type="text" name="userName" /><br /><br />
	<b>Password:</b><input type="password" name="password" />
	<input type="submit" value="Login" />
</form>
<br /><a href="CreateNewAccount.html">Create New Account</a>
</body>
</html>