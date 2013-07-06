<%@page import="java.util.ArrayList"%>
<%@page import="dbAccess.DBAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="web.config.*"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.Iterator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main quiz list</title>
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
</head>
<body>

	<%
		if (request.getSession().getAttribute("userName") == null) {
			String redirectURL = "home.jsp";
			response.sendRedirect(redirectURL);
		}
	%>

	<%
		String user_name = (String) session.getAttribute("userName");
		DataBaseConnection db = (DataBaseConnection) request
				.getServletContext().getAttribute("Database");
		DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
	%>

<div id="main">		
	<div class="organize" >
		<div class="putleft" >
		
		<h3>Popular Quizzes</h3>
		<br>
			<%
			{
					ArrayList<String> quiz_names = dbAccess.getQuizByPopularity("");
					out.println(Quiz.toHtmlTable(quiz_names, "", dbAccess));
			}
			%>
		</div>
		<div class="putleft" >
		<h3>Newest Quizzes</h3>
		<br>
			<%
			{
					ArrayList<String> quiz_names = dbAccess.getQuizByCreateTime("");
					out.println(Quiz.toHtmlTable(quiz_names, "", dbAccess));
			}
			%>
		</div>
		<div class="endpanel"></div>
		
		<div class="putleft" >
		<h3>Quizzes by name</h3>
		<br>
			<%
			{
					ArrayList<String> quiz_names = dbAccess.getQuizNames();
					out.println(Quiz.toHtmlTable(quiz_names, "", dbAccess));
			}
			%>
		</div>
		
		<div class="putleft" >
		<h3>Your Creations</h3>
		<br>
			<%
			{	
					ArrayList<String> quiz_names = dbAccess.getQuizByUserNames(user_name, "");
					out.println(Quiz.toHtmlTable(quiz_names, "", dbAccess));
			}
			%>
		</div>
		
				
		<div class="endpanel"></div>
	</div>
</div>
</body>
</html>