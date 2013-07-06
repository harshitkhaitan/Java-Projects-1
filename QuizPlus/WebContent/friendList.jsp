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
<title>Inbox for <%= request.getSession().getAttribute("userName") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<div class="putWideleft" >
	    <H3>Friends List</H3>

		<br>
			<a href="SendMessage.jsp">New Message</a>
			&nbsp

			<a href="SendFriendRequest.jsp">New Friend Request</a>
			&nbsp 

			<a href="SendChallenge.jsp">New Challenge</a>
			<br /> &nbsp <br />

				
<TABLE class="inbox">
   <TR>
      <TH>Profile Page</TH>
      <TH>Send Message</TH>
      <TH>Send Challenge</TH>   
      <TH>Remove Friend</TH>   
      
<%
		DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		String username = (String) request.getSession().getAttribute("userName");
  		ArrayList<Friend> friends = db.getFriends(username);
  		if (friends == null) System.out.println("No Friends");
		for(Friend friend: friends){
			String name = friend.getUsername();

			out.println("<TR ALIGN=\"CENTER\">");
			out.println("<TD>");
			String fromProfile = "<a href=\"profile.jsp?username=" + name + "\">" + name + "</a>";
			out.println(fromProfile);
			out.println("</TD>");
			
			out.println("<TD>");
			out.println("<a href=\"SendMessage.jsp?toUser=" + name + "\">");
			out.println("Send Message");
			out.println("</a>");
			out.println("</TD>");

			out.println("<TD>");
			out.println("<a href=\"SendChallenge.jsp?toUser=" + name + "\">");
			out.println("Send Challenge");
			out.println("</a>");
			out.println("</TD>");
			
			out.println("<TD>");
			out.println("<a href=\"removeFriend.jsp?toUser=" + name + "\">");
			out.println("Remove Friend");
			out.println("</a>");
			out.println("</TD>");
			
			out.println("</TR>");

		}
%>
</TABLE>

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