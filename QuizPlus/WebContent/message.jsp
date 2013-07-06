<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.config.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Message for <%= request.getSession().getAttribute("userName") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<div class="putleft" >

 
<%
		
		DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		String username = (String) request.getSession().getAttribute("userName");
		int ID = Integer.parseInt(request.getParameter("id"));
		Message message  = db.getMyMessage(username, ID);
		String fromUser = message.getFrom();
		String fromProfile = "<a href=\"profile.jsp?username=" + fromUser + "\">" + fromUser + "</a>";
		
		if (message.getType()==0){
	 		out.println("<h3><b>Message From: </b>" + fromProfile + "</h3><br>") ;
	 		out.println("<b>Message Time: </b>" + message.getTimestamp().toString() + "<br> <br>") ;
			out.println("<b>Message: </b><br><textarea cols=\"45\" rows=\"9\" readonly=\"1\"> " + message.getText() + "</textarea>");			
		//	System.out.println("<b>Message: </b>" + message.getText());						
		}
		if (message.getType()==1){
	 		out.println("<h3><b>Friend Request From: </b>" + fromProfile + "</h3><br>") ;
	 		out.println("<b>Request Time: </b>" + message.getTimestamp().toString() + "<br> <br>") ;
			out.println("<b>Message: </b><br><textarea cols=\"45\" rows=\"9\" readonly=\"1\"> " + message.getText() + "</textarea>");			
			out.println("<br>");
			out.println("&nbsp");
			out.println("<form action=\"FriendRequestServlet\" method=\"post\">");
			out.println("<input type=\"hidden\" name=\"ID\" value=\""+ message.getId() + "\" />");
			out.println("<input type=\"hidden\" name=\"from\" value=\""+ message.getFrom() + "\" />");
			out.println("<input type=\"submit\" name=\"submitButton\" value=\"Accept\" />");
			out.println("<input type=\"submit\" name=\"submitButton\" value=\"Reject\" />");
			out.println("</form>");

		}
		if (message.getType()==2){
	 		out.println("<h3><b>Challenge From: </b>" + fromProfile + "</h3><br>") ;
	 		out.println("<b>Challenge Time: </b>" + message.getTimestamp().toString() + "<br> <br>") ;
			out.println("<b>Message: </b><br><textarea cols=\"45\" rows=\"9\" readonly=\"1\"> " + message.getText() + "</textarea>");			
			out.println("<br>");
	 		out.println("<b>My Best Score: </b>" + message.getMyScore() + "<br>") ;
	 		String quiz_page = "<a href=\"quiz_summary.jsp?quiz_name=" + message.getQuizName() +"\">Accept Challenge</a>";
	 		out.println("<b>Challenge for Quiz: </b>" + message.getQuizName() + "    <Br />" + quiz_page + "<br>") ;

			out.println("");
			
		}
		

			
%>
<br /><br /><button onClick="location.href = 'SendMessage.jsp?toUser=<%= message.getFrom() %>'">Reply</button>

<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>
<input type="button" value="Back" onclick="goBack()" />

		</div>
		<div class="endpanel"></div>		
	</div>
</div>

</body>
</html>