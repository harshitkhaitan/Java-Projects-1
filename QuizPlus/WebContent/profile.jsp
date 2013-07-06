<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.config.*" %>
<%@ page import="quiz.*"%>
<%@ page import="dbAccess.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title><%= request.getParameter("username") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<div class="putleft" >
	

<%
String user = request.getParameter("username");
DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
boolean exists = db.checkIfExists(user);
if (exists){
	out.println("<h3><b> "  + user + "'s </b> profile</h3><br>");	
	out.println("<a href=\"SendFriendRequest.jsp?toUser=" + user + "\">Add as a friend</a><br />");

	

    out.println("<p><b> Achievements </b></p>");

	ArrayList<Achievement> achievments = db.getUserAchievements(user, dbAccess);
	out.println(Achievement.toHtmlTable(achievments, ""));
	
	out.println("<p><b> Quiz Creations</b></p>");
	
	ArrayList<String> user_quiz_names = dbAccess.getQuizByUserNames(user, "LIMIT 5");
	out.println(Quiz.toHtmlTable(user_quiz_names, "", dbAccess));
	

	out.println("<p><b> Recent Quiz Attempts</b></p>");
	
	ArrayList<QuizStats> quiz_stats = dbAccess.getStatsForUser(user, "LIMIT 5");
	out.println(QuizStats.toHtmlTable(quiz_stats, ""));
	
	
}else{
	out.println("User " + user + " does not exist");
}

%>

&nbsp <br />
<input type="button" value="Back" onclick="goBack()" />

<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>

		</div>
		<div class="endpanel"></div>		
	</div>
</div>
</body>
</html>