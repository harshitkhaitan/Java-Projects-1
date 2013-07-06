<%@page import="java.util.ArrayList"%>
<%@page import="dbAccess.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="web.config.*"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.Iterator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
</head>
<body>
	<%
		String user_name = (String) session.getAttribute("userName");
		String quiz_name = request.getParameter("quiz_name");
		DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
		
		Quiz quiz = new Quiz(quiz_name, dbAccess);
		out.println(quiz.toHTML(user_name, dbAccess));
	%>
</body>
</html>